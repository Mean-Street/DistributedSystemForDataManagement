package spark

import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.querybuilder.QueryBuilder

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import server.GlobalVars

object Compute {
  def getAllWeather: String = {
    //====================
    val conf = new SparkConf().setAppName("test").setMaster("spark://" + GlobalVars.sparkAddress + ":" + GlobalVars.sparkPort) //setMaster("local[2]"); //setMaster("spark://@:port")
    val sc: SparkContext = new SparkContext(conf);

    val rdd = sc.cassandraTable("sdtd", "temperatures");

    sc.stop

    /*val resArray = rdd.collect.toArray
    compact(render(resArray))*/ //Erreur d'importation des librairies : resArray n'est pas convertie en JValue
    
    rdd.toString
    //======================
  }
}