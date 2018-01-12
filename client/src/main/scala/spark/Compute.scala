package spark

import com.datastax.spark.connector.CassandraRow
import com.datastax.spark.connector._

import java.text.SimpleDateFormat
import java.util.Calendar


import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.ml.feature.VectorAssembler
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{ SparkSession, Row }

/**
 * Constants related to Cassandra's table
 */
object DataBase {
  val keyspace: String = "sdtd"

  object Temperature {
    val table: String = "temperature"
    val temperature: String = "temperature"
    val date: String = "date"
  }

  object Feeling {
    val table: String = "tweet"
    val feeling: String = "sentiment"
    val date: String = "date"
  }

  def parseDate(date: String): Calendar = {
    val calendar: Calendar = Calendar.getInstance()
    calendar.setTime(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(date))
    calendar
  }
}


/**
 * Main object where the computation takes place
 */
object Compute {
  val sc = new SparkContext(new SparkConf())

  /**
  * The type extracted from a row of a table in the database
  */
  trait Entry[A <: Entry[A]] {
    def hourSlot: Calendar
    def /(div: Double): A
    def +(that: A): A
  }

  /**
  * The type representing a row of the table: temperature.
  * The field 'hourSlot' represents the date when was recorded the associated temperature truncated to its hour
  */
  case class EntryTemperature(hourSlot: Calendar, temperature: Double) extends Entry[EntryTemperature] {
    def /(div: Double) = EntryTemperature(this.hourSlot, this.temperature / div)
    def +(that: EntryTemperature) = EntryTemperature(this.hourSlot, this.temperature + that.temperature)
  }
  object EntryTemperature {
    /**
    * A handy constructor, which from a row of the table, creates an EntryTemperature
    */
    def apply(row: CassandraRow): EntryTemperature = {
      EntryTemperature(DataBase.parseDate(row.getString(DataBase.Temperature.date)),
                       row.getDouble(DataBase.Temperature.temperature))
    }
  }

  /**
  * The type representing a row of the table: tweet.
  * The field 'hourSlot' represents the date when was recorded the associated sentiment truncated to its hour
  */
  case class EntryFeeling(hourSlot: Calendar, feeling: Double) extends Entry[EntryFeeling] {
    def /(div: Double) = EntryFeeling(this.hourSlot, feeling / div)
    def +(that: EntryFeeling) = EntryFeeling(this.hourSlot, this.feeling + that.feeling)
  }
  object EntryFeeling {
    /**
    * A handy constructor, which from a row of the table, creates an EntryFeeling
    */
    def apply(row: CassandraRow): EntryFeeling = {
      EntryFeeling(DataBase.parseDate(row.getString(DataBase.Feeling.date)),
                   row.getString(DataBase.Feeling.feeling).toDouble)
    }
  }

  /**
  * The class which stores the result of the computation, i.e., the average sentiment and temperature
  * for a given hour, represented by the attribute 'hourSlot'.
  */
  case class AveragePair(hourSlot: Calendar, feeling: Double, temperature: Double)

  type HourSlot = Int

  /**
  * Computes the average of an Entry for each hour, from 'begin' to 'end'. It generates an RDD
  * of the provided Entry class and an identifier generated from its hour and day.
  */
  private def averageInterval[A <: Entry[A]](rdd: RDD[A], begin: Calendar, end: Calendar): RDD[(HourSlot, A)] = {
    def getSlotId(hourSlot: Calendar): HourSlot = (
       hourSlot.get(Calendar.YEAR).toString
       + hourSlot.get(Calendar.MONTH).toString
       + hourSlot.get(Calendar.DAY_OF_MONTH).toString
       + hourSlot.get(Calendar.HOUR).toString
      ).toInt

    rdd.map(et => (getSlotId(et.hourSlot), et))
       // Filter entries outside of interval
       .filter(v => getSlotId(begin) <= v._1 && v._1 <= getSlotId(end))
       // Add a counter to each entry
       .map(v => (v._1, (v._2, 1)))
       // Reduce by hour slot
       .reduceByKey((v1, v2) => (v1._1 + v2._1, v1._2 + v2._2))
       // Divide by aggregated counter
       .map(v => (v._1, v._2._1 / v._2._2))
  }

  /**
  * Loads the sentiments from the database and groups them by hour, then computes every averages.
  */
  private def loadFeelings(begin: Calendar, end: Calendar): RDD[(HourSlot, EntryFeeling)] = {
    val rdd = sc.cassandraTable(DataBase.keyspace, DataBase.Feeling.table)
    averageInterval[EntryFeeling](rdd.map(EntryFeeling(_)), begin, end)
  }

  /**
  * Loads the temperatures from the database and groups them by hour, then computes every averages.
  */
  private def loadTemperatures(begin: Calendar, end: Calendar): RDD[(HourSlot, EntryTemperature)] = {
    val rdd = sc.cassandraTable(DataBase.keyspace, DataBase.Temperature.table)
    averageInterval[EntryTemperature](rdd.map(EntryTemperature(_)), begin, end)
  }

  /**
  * For a given list of average sentiments and temperatures, computes the a linear regression and
  * returns the coefficient of determination.
  */
  private def correlate(feelings: RDD[(HourSlot, EntryFeeling)],
                        temperatures: RDD[(HourSlot, EntryTemperature)]): (Array[AveragePair], Double) = {
    val rdd: RDD[AveragePair] = feelings
        .join(temperatures)
        .map(v => AveragePair(v._2._1.hourSlot, v._2._1.feeling, v._2._2.temperature))

    val schema = StructType(Seq(StructField("feeling", DoubleType, false),
                                StructField("temperature", DoubleType, false)))


    val ss = SparkSession.builder.getOrCreate()
    val df = ss.createDataFrame(rdd.map(v => Row(v.feeling, v.temperature)), schema)
    val assembler = new VectorAssembler()
        .setInputCols(Array("temperature"))
        .setOutputCol("features")
        .transform(df)

    val model = new LinearRegression().setLabelCol("feeling")
                                      .setFeaturesCol("features")
                                      .setMaxIter(10)
                                      .setRegParam(0.3)
                                      .setElasticNetParam(0.8)
                                      .fit(assembler)
    (rdd.collect(), model.summary.r2)
  }

  /**
  * Loads the tables 'temperature' and 'tweet' from the database and computes:
  *   - the average temperature and sentiment for every hour of the selected interval
  *   - a linear regression between the two previous results and returns the coefficient of determination
  */
  def processMagic(begin: Calendar, end: Calendar): (Array[AveragePair], Double) = {
    val feelings: RDD[(HourSlot, EntryFeeling)] = loadFeelings(begin, end)
    val temperatures: RDD[(HourSlot, EntryTemperature)] = loadTemperatures(begin, end)
    correlate(feelings, temperatures)
  }
}
