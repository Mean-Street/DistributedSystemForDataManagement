#!/bin/bash

. ./env.sh

UBUNTU_ID=ami-fcc4db98
REPO_FOLDER="~/smack"
CLONE_REPO_CMD="git clone https://github.com/Mean-Street/DistributedSystemForDataManagement $REPO_FOLDER"
INIT_CMD="./init-ec2.sh"
USER_DATA_FILE="user_data.txt"
COMMON_ARGS="--count 1 --image-id $UBUNTU_ID --key-name $KEY_PAIR"

function tags {
   echo "--tag-specifications ResourceType=instance,Tags=[{Key=Name,Value=$1}]"
}

function generate_user_data {
    echo "#!/bin/bash" > $USER_DATA_FILE
    echo $CLONE_REPO_CMD >> $USER_DATA_FILE
    echo cd "$REPO_FOLDER/$1" >> $USER_DATA_FILE
    echo $INIT_CMD >> $USER_DATA_FILE
}

function security_group {
    echo "--security-groups ${SECURITY_GROUPS[$1]}"
}

# TODO: user data not executed
generate_user_data zookeeper
aws ec2 run-instances $COMMON_ARGS --instance-type t2.micro $(tags zookeeper) $(security_group zookeeper) --user-data "file://$USER_DATA_FILE"
