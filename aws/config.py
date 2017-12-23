import os

ROOT = os.path.realpath(os.path.join(__file__, "..", ".."))

KEY_PAIR_NAME = "smack"
KEY_PAIR_PATH = os.path.join(ROOT, KEY_PAIR_NAME + ".pem")

ZOOKEEPER_SECURITY_GROUP = "zookeeper"
KAFKA_SECURITY_GROUP = "kafka"
CASSANDRA_SECURITY_GROUP = "cassandra"

UBUNTU_IMAGE_ID = "ami-fcc4db98"
