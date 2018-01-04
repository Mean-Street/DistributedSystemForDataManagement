import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

object BasicTest {
  def main(args: Array[String]) {

    val sc = new SparkContext(new SparkConf())
    val lines = sc.textFile("data.txt")
    val lineLengths = lines.map(s => s.length)
    val totalLength = lineLengths.reduce((a, b) => a + b)
    println("OUTPUT:", totalLength)

  }
}
