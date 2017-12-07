package ClientApi

import akka.actor.ActorSystem
import akka.http.scaladsl.settings.ServerSettings

object HttpServer {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("weather-wand")

    WeatherRoutes.startServer("localhost", 6666, ServerSettings(system), system)

    system.terminate()
  }

}
