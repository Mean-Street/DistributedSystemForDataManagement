package server

import akka.actor.{ Actor, Props }

import java.util.Calendar

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

import spark.Compute

//Modèle d'objet 'Weather'
final case class Weather(temperature: Double, city: String, date: String) {
  override def toString() : String = "La température était de " + temperature + " degrés à " + city + " le " + date + ".\n"
}

object WeatherActor {
  //final case class Output(description: String)
  final case class GetWeather(begin: Calendar, end: Calendar)
  // val sc = new SparkContext(new SparkConf())
  // val computeAverageInterval = new Compute

  def props: Props = Props[WeatherActor]
}

class WeatherActor extends Actor {
  import WeatherActor._

  def receive: Receive = {
    case GetWeather(begin, end) =>
      sender ! Compute.processMagic(begin, end)
  }
}
