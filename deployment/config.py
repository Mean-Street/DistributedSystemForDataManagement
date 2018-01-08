import os

ROOT = os.path.realpath(os.path.join(__file__, "..", ".."))

KEY_PAIR_NAME = "smack"
KEY_PAIR_PATH = os.path.join(ROOT, KEY_PAIR_NAME + ".pem")

ZOOKEEPER_SECURITY_GROUP = "zookeeper"
KAFKA_SECURITY_GROUP = "kafka"
CASSANDRA_SECURITY_GROUP = "cassandra"
MESOS_MASTER_SECURITY_GROUP = "mesos_master"

UBUNTU_USER = "ubuntu"
UBUNTU_IMAGE_NAME = "ubuntu/images/hvm-ssd/ubuntu-xenial-16.04-amd64-server-20171121.1"
PROJECT_TAG_KEY = 'Project'
PROJECT_TAG_VALUE = 'smack'
MASTER_TAG_KEY = 'IsMaster'
MASTER_TAG_VALUE = '1'
SLAVE_TAG_VALUE = '0'

MESOS_REPO_DISTRIB = 'ubuntu'
MESOS_REPO_CODENAME = 'xenial'
