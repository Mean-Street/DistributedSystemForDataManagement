# Zookeeper

## Deployment on AWS

* Create an EC2 instance (Ubuntu, micro is sufficient)
* Open the port `2181` in the security group of the instance
* Connect through SSH and run:

```bash
sudo apt-get update
sudo apt-get install -y docker.io

# Open ports in the firewall
sudo ufw disable # TODO: something cleaner

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

If the port `2181` is filtered, you need to add it to the security group in AWS:
`Type="Custom TCP"`, `Port Range=2181`, `Source="Anywhere"`, `Description="zookeeper"`

If it is closed, it can be for at least two reasons:

* The Docker container is not running
* The firewall forbids inbound traffic on this port
