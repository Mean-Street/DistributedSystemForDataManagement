#!/bin/bash

UBUNTU_ID=ami-fcc4db98
REPO_FOLDER="~/smack"
CLONE_REPO_CMD="git clone https://github.com/Mean-Street/DistributedSystemForDataManagement $REPO_FOLDER"
INIT_CMD="./init-ec2.sh"

# TODO: user data does not work
aws ec2 run-instances --count 1 --image-id $UBUNTU_ID --instance-type t2.micro \
                      --key-name $KEY_PAIR --security-groups $ZOOKEEPER_SECU_GROUP \
                      --tag-specifications 'ResourceType=instance,Tags=[{Key=Name,Value=zookeeper}]' \
                      --user-data "$CLONE_REPO_CMD ; cd $REPO_FOLDER/zookeeper ; $INIT_CMD"
