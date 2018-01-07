# Akka Http Web Server

## TODO

* Tests on AWS

## Prerequisites

* Up and running Spark machine connected to Cassandra

## Deployment

Just build & run the docker file with 3 arguments : 
* the port for the webserver
* the address of the Spark machine
* the port used by the Spark machine

Example :
```
docker build --build-args --build-arg sparkAddress=<@> --build-arg sparkPort=<port>
```
Don't forget to bind the ports of the docker with the machine. The docker container uses the port 8080.
```
docker run -p <machinePort>:8080
```

## How to use
Without docker :
```
sbt compile
sbt run
```
Then either enter the webserver address in a browser or use the command 
```
curl http://<address>:<machinePort>/weather
```
Available routes :
* /weather to get all table entries

## How to expand
All the routes are in the file "Routes.scala". To add a new route, just add the following example after the weather route :
```
path("<a new path>") {
    get {
        onComplete(weatherActor ? <method to call>) {
        	case util.Success(value) => complete(200, value.toString)
        	case util.Failure(ex) => complete(500, ex.toString)
    	}
    }
}
```
Then add the \<method to call\> as a case into WeatherActor.scala following the GetWeather example :
```
case <method to call> =>
      val res = Compute.<a method>
      sender() ! res  //== send res to the sender object ! res must be marshallable !
```
To finish with, add the body of the called method into Compute.scala (or in another class) following the Compute.getAllWeather example.