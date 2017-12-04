package WeatherWizard;



import WeatherWizard.Requesters.RequestDispacher;

import akka.NotUsed;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.http.javadsl.ConnectHttp;
import akka.http.javadsl.Http;
import akka.http.javadsl.ServerBinding;
import akka.http.javadsl.model.HttpRequest;
import akka.http.javadsl.model.HttpResponse;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.Flow;

import java.io.IOException;
import java.util.concurrent.CompletionStage;

public class App
{
    public App() {
    }

    public static void main(String[] args) throws IOException
    {
        ActorSystem system = ActorSystem.create("WeatherWizard");

        // http and materializer are needed to send and receive http requests
        Http http = Http.get(system);
        Materializer materializer = ActorMaterializer.create(system);

        ActorRef requesterRef = system.actorOf(RequestDispacher.props(http, materializer), RequestDispacher.name);

        HttpServer server = new HttpServer(requesterRef);

        final Flow<HttpRequest, HttpResponse, NotUsed> routeFlow = server.createRoute().flow(system, materializer);
        final CompletionStage<ServerBinding> binding = http.bindAndHandle(routeFlow,
                ConnectHttp.toHost("localhost", 8080), materializer);

        System.out.println("Server online at http://localhost:8080/\nPress RETURN to stop...");
        System.in.read(); // let it run until user presses return

        binding.thenCompose(ServerBinding::unbind)
                .thenAccept(unbound -> system.terminate());
    }
}
