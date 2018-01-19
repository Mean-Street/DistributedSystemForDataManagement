#===--- Dockerfile for the Akka WeatherFinder app ---===

# Base image
FROM maven

# Copy the source files from your app.
# Source path relative to this Dockerfile path. Modify the destination folder accordingly.
COPY . /tmp/SDTD/WeatherFinder/

# Set the working directory.
WORKDIR /tmp/SDTD/WeatherFinder/app

# Compile the sources.
RUN mvn compile

# Set the starting shell script.
ENTRYPOINT /tmp/SDTD/WeatherFinder/docker_entrypoint.sh
