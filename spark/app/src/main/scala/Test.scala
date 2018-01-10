import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object Test {
  def main(args: Array[String]) {

    val sc = new SparkContext(new SparkConf())
    val lines = sc.parallelize(Array("a", "bc", "def"))
    val lineLengths = lines.map(s => s.length)
    val totalLength = lineLengths.reduce((a, b) => a + b)
    println("-----------------------")
    println("ARGS:", args.mkString(" "))
    println("TOTAL LENGTH:", totalLength)
    println("-----------------------")
  }
}
