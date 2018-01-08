package spark

import com.datastax.spark.connector.CassandraRow
import com.datastax.spark.connector._

import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import server.GlobalVars


/**
 * All the computation is done here. It defines the following functions:
 *  - ProcessMagic: for a given interval calculates the hourly temperatures and feelings averages
 */
object Compute {
  case class DataEntry(hour: String, temperature: Double, feeling: Double) {
    def +(that: DataEntry) = DataEntry(this.hour, this.temperature + that.temperature, this.feeling + that.feeling)
    def /(div: Double) = DataEntry(this.hour, this.temperature / div, this.feeling / div)
      override def toString: String = hour + " - " + temperature.toString + " - " + feeling.toString
  }

  def getSlotId(date: String): Int = {
    val calendar: Calendar = Calendar.getInstance()
    calendar.setTime(new SimpleDateFormat("dd/mm/yyyy hh").parse(date))
    (calendar.get(Calendar.YEAR).toString
     + calendar.get(Calendar.MONTH).toString
     + calendar.get(Calendar.DAY_OF_MONTH).toString
     + calendar.get(Calendar.HOUR).toString).toInt
  }

  def createKeyValue(entryString: CassandraRow): (Int, (DataEntry, Int)) = {
    val hourSlotPattern = "(../../.... ..):..:..".r
    val hourSlotPattern(slot) = entryString.getString("date")
    (getSlotId(slot), (DataEntry(slot, entryString.getDouble("temperature"), entryString.getDouble("feeling")), 1))
  }

 /**
 * This function requires two inputs matching the following pattern: "dd/mm/yyyy hh" and returns
 *  the hourly temperatures and feelings averages.
 */
  def processMagic(start: String, end: String): List[DataEntry] = {
    val startId: Int = getSlotId(start)
    val endId: Int = getSlotId(end)

    val sc: SparkContext = new SparkContext(new SparkConf());
    val rdd = sc.cassandraTable("test", "temperatures");

    rdd.map(createKeyValue)
        // Filters out the entries not included in the provided range
        .filter(v => startId <= v._1 && v._1 <= endId)
        .reduceByKey((v1, v2) => (v1._1 + v2._1, v1._2 + v2._2))
        .map(v => v._2._1 / v._2._2)
        .collect()
        .toList
  }
}
