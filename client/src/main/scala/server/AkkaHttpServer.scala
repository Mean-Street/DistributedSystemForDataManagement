package server

import akka.actor.{ ActorRef, ActorSystem }
import akka.stream.ActorMaterializer
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.http.scaladsl.server.Directives._
import scala.concurrent.{ ExecutionContext, Future }

object GlobalVars {
  var serverPort: String = "8080"
  var sparkAddress: String = "0.0.0.0"
  var sparkPort: String = "7077"
}

object AkkaHttpServer extends App with Routes {

  // ! pas de tests pour les arguments !
   // if (args.length == 2) {
   //   GlobalVars.sparkAddress = args(1)
   //   GlobalVars.sparkPort = args(2)
   // }

  // set up ActorSystem and other dependencies here
  implicit val actorSystem = ActorSystem("system")
  implicit val actorMaterializer = ActorMaterializer()
  // Needed for the Future and its methods flatMap/onComplete in the end
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher
  val weatherActor: ActorRef = actorSystem.actorOf(WeatherActor.props, "weatherActor")

  // Evaluate Spark Context at bootstart
  // WeatherActor.sc

  val routes = httpRoutes;

  val serverBindingFuture: Future[ServerBinding] = Http().bindAndHandle(routes, "0.0.0.0", 8080)

  println(s"Server online at http://0.0.0.0:8080/\nPress RETURN to stop...")

  /*  //=============readLine not blocking when deployed on AWS===================
  StdIn.readLine()

  serverBindingFuture
    .flatMap(_.unbind())
    .onComplete { done =>
      actorSystem.stop(weatherActor)
      actorSystem.shutdown
    }
  */
}
