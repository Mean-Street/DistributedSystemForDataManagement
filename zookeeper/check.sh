#!/bin/bash

sudo apt-get install -y nmap
nmap -p $2 -Pn $1
