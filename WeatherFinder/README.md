# Akka WeatherFinder app

## Sources

The app source files are located in the app sub-directory.

## Makefile
Rules: build, run, login, push
You have to change the app name if you re-use this Makefile.

## Dockerfile

The Dockerfile used to create the app docker image.
NB: Put the app compilation in here (ex: RUN mvn compile), so that we dont have to compile every time we deploy the app.

## Docker entrypoint script

The docker_entrypoint.sh script is the one launched by the container. If you need to specify command line arguments, add them here.

## Build docker image

```bash
make build
```

## Run the docker container (for local testing)
```bash
make run
```

## Push the image to the DockerHub repository

```bash
make
```
This will login and push the image.
If you only need one of these steps:

### Login
```bash
make login
```
You then need to enter the password.

### Push to the DockerHub repository
```bash
make push
```
