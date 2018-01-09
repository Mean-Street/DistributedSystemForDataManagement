import os

ROOT = os.path.realpath(os.path.join(__file__, "..", ".."))

KEY_PAIR_NAME = "smack"
KEY_PAIR_PATH = os.path.join(ROOT, KEY_PAIR_NAME + ".pem")

GLOBAL_SECURITY_GROUP = "global"
MESOS_SECURITY_GROUP = "mesos"
MARATHON_SECURITY_GROUP = "marathon"

UBUNTU_USER = "ubuntu"
UBUNTU_IMAGE_NAME = "ubuntu/images/hvm-ssd/ubuntu-xenial-16.04-amd64-server-20171121.1"
PROJECT_TAG_KEY = 'Project'
PROJECT_TAG_VALUE = 'smack'
MASTER_TAG_KEY = 'IsMaster'
MASTER_TAG_VALUE = '1'
SLAVE_TAG_VALUE = '0'

MESOS_REPO_DISTRIB = 'ubuntu'
MESOS_REPO_CODENAME = 'xenial'
