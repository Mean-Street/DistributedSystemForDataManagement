# Zookeeper

## Deployment on AWS

* Create an EC2 instance (Ubuntu, micro is sufficient)
* Open the port `2181` in the security group of the instance
* Connect through SSH and run:

```bash
git clone https://github.com/Mean-Street/DistributedSystemForDataManagement sdtd
cd sdtd/zookeeper
./init-ec2.sh
sudo ./run_zookeeper.sh 2181
```

* Check if Zookeeper is running:

```bash
# On another machine
cd ~/sdtd/zookeeper
./check.sh ZOOKEEPER_IP 2181
```

If the port `2181` is filtered, you need to add it to the security group in AWS:
`Type="Custom TCP"`, `Port Range=2181`, `Source="Anywhere"`, `Description="zookeeper"`

If it is closed, it can be for at least two reasons:

* The Docker container is not running
* The firewall forbids inbound traffic on this port
