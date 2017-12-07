# Zookeeper

## Deployment on AWS

* Create an EC2 instance (micro is sufficient)
* Open the port `2181` in the security group of the instance
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install docker.io

# Open port 2181 in the firewall
sudo ufw disable # TODO: something cleaner
# On CentOS:
# firewall-cmd --zone=trusted --add-interface=docker0

git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/zookeeper
sudo ./run_zookeeper.sh 2181
```
