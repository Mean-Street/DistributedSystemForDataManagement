#!/bin/bash

export KEY_PAIR=smack

# Ports
export ZOOKEEPER_PORT=2181

# Security groups
declare -A SECURITY_GROUPS
SECURITY_GROUPS[zookeeper]=zookeeper
export SECURITY_GROUPS
