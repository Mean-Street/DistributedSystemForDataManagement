package server

import akka.actor.{ Actor, Props }

import spark.Compute

//Modèle d'objet 'Weather'
final case class Weather(temperature: Double, city: String, date: String) {
  override def toString() : String = "La température était de " + temperature + " degrés à " + city + " le " + date + ".\n"
}

object WeatherActor {
  //final case class Output(description: String)
  final case object GetWeather

  def props: Props = Props[WeatherActor]
}

class WeatherActor extends Actor {
  import WeatherActor._

  def receive: Receive = {
    case GetWeather =>
      //récupérer ici ce qu'on veut envoyer. Exemple : val res = Weather(5.5, "Paris", "21/12/17")
      val res = Compute.getAllWeather
      sender() ! res  //== on envoie le contenu de res à l'objet sender()
  }
}