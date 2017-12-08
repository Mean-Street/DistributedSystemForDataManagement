# Akka - Weatherfinder

## Deployment on AWS
- Create an EC2 instance (Ubuntu, micro is sufficient)
- Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install -y docker.io

# Open ports in the firewall
sudo ufw disable # TODO: something cleaner

git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/WeatherFinder
```
- Run WeatherFinder
```bash
sudo ./run_weatherwizard.sh <KAFKA_IP> <KAFKA_PORT> <TOPIC>
```
