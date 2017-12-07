# Zookeeper

## Deployment on AWS

* Create an EC2 instance (micro is sufficient)
* Open the port `2181` in the security group of the instance
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install -y docker.io

# Open port 2181 in the firewall
sudo ufw disable # TODO: something cleaner
# On CentOS:
# firewall-cmd --zone=trusted --add-interface=docker0

git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/zookeeper
sudo ./run_zookeeper.sh 2181
```

* Check if Zookeeper is running:

```bash
sudo netstat -lptu
# tcp6   0   0 [::]:2181   [::]:*   LISTEN   3417/docker-proxy

# On another machine
sudo apt-get install -y nmap
nmap -p 2181 -Pn <ZOOKEEPER_IP>
# PORT     STATE SERVICE
# 2181/tcp open  unknown
```
