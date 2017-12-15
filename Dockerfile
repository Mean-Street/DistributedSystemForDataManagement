FROM maven
ADD WeatherFinder /tmp/SDTD/WeatherFinder/
WORKDIR /tmp/SDTD/WeatherFinder
ENTRYPOINT /tmp/SDTD/WeatherFinder/docker_entrypoint.sh
