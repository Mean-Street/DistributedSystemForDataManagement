#!/bin/bash

. ./env.sh

# Create a key pair for ssh
key_path="../$KEY_PAIR.pem"
rm -f $key_path
aws ec2 create-key-pair --key-name $KEY_PAIR --query 'KeyMaterial' --output text > $key_path
chmod 400 $key_path

# Create security groups
aws ec2 create-security-group --group-name $ZOOKEEPER_SECU_GROUP --description "Zookeeper"
if [ $? -eq 0 ]; then
  aws ec2 authorize-security-group-ingress --group-name $ZOOKEEPER_SECU_GROUP --protocol tcp --port 22 --cidr 0.0.0.0/0
  aws ec2 authorize-security-group-ingress --group-name $ZOOKEEPER_SECU_GROUP --protocol tcp --port $ZOOKEEPER_PORT --cidr 0.0.0.0/0
fi
