# Akka Http Web Server

## TODO

* Tests on AWS

## Prerequisites

* Up and running Cassandra database
* At minimum a small machine (sbt needs RAM)

## Test

### Web server
In order to test if the client is reachable, run:
```sh
make test_client client_host=<host>
```

The string "test réussi" should be echoed.

### Flow Akka - Spark - Cassandra
To test the flow, run first the following command on the machine running cassandra's container:
```sh
make create_test cassandra_container=<name>
```

Then, on the machine running the client, execute:
```sh
make test_cassandra cassandra_host=<host> client_host=<host>
```
This command should return the following string:
```json
{"res":[{"hourSlot":"2018-01-01 14","feeling":1.6666666666666667,"temperature":7.0},{"hourSlot":"2018-01-01 03","feeling":2.0,"temperature":7.0}],"r2":0.0}
```

To clear the created test keyspace, run `make clear_test cassandra_container=<name>` on cassandra's machine.

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
Date is a string formatted: ""yyyy-MM-dd'T'HH:mm:ss".
The HTTP request is a post with JSON formatted like: {"begin": "yyyy-MM-dd HH", "end": "yyyy-MM-dd HH"}

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
