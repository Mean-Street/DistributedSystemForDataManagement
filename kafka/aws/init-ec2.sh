#!/bin/bash

sudo apt-get update
sudo apt-get install -y docker.io

# Open ports in the firewall
sudo ufw disable # TODO: something cleaner
