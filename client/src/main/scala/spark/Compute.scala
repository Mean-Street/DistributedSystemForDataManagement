package spark

import com.datastax.spark.connector._
import org.apache.spark.sql.cassandra

import com.datastax.driver.core.Cluster
import com.datastax.driver.core.querybuilder.QueryBuilder

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import org.json4s.native.JsonMethods._
import org.json4s.JsonDSL._
import org.json4s._

import server.GlobalVars

object Compute {
	def getAllWeather: String = {
		/*===============Version test sans Spark===============
		val cluster = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042).build();
		val session = cluster.connect();
		//val statement = QueryBuilder.select().all().from("sdtd", "temperatures");
		val statement = "SELECT JSON * FROM sdtd.temperatures"
		val requestQuery = session.execute(statement);
		val it = requestQuery.all().iterator();
		var res : String = "";
		while(it.hasNext()) {
			//println(it.next().getString("city") + " : " + it.next().getDouble("temperature"))

			//println(it.next().getString(0)) // == println(it.next().getString("[json]")) // == récupère ligne sous format JSon
			res = res + it.next().getString(0) + "\n";

		}
		session.close();
		cluster.close();
		res; //retour
		*///=====================================================
		
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

