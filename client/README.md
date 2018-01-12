# Akka Http Web Server

## TODO

* Tests on AWS

## Prerequisites

* Up and running Cassandra database
* At minimum a small machine (sbt needs RAM)

## Test

* Requires a database
* Run the queries contained in `client/mock_queries.csql` in a cqlshell
* Build the image and run the container with the following commands:
```sh
make build
make run cassandra_host=<host> cassandra_port=<port>
```

Once the server up and running, and the user is ready to be flabbergasted, send a post request with `curl` or any other
tool such as the following one:
```sh
curl -H "Content-Type: application/json" -X POST -d '{"begin":"01/01/2018 01","end":"01/01/2018 03"}' http://localhost:8080/weather
```


## Deployment

Just build & run the docker file with 3 arguments :
* the port for the webserver
* the address of the Spark machine
* the port used by the Spark machine

Example :
```sh
make build
make run cassandra_host=<host> cassandra_port=<port>
```

## Notes on Cassandra
Date is a string formatted: "dd/mm/yyyy hh:mm:ss".
The HTTP request is a post with JSON formatted like: {"begin": "dd/mm/yyyy hh", "end": "dd/mm/yyyy hh"}

## How to use
Without docker :
```sh
sbt compile
sbt run
```
Then either enter the webserver address in a browser or use the command 
```
curl http://<address>:<machinePort>/weather
```
Available routes :
* /example to test the connection to the server
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
