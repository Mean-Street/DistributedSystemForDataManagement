# Configuration

You need to add the docker interface to the trusted group to communication between dockers.
firewall-cmd --zone=trusted --add-interface=docker0
