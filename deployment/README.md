# AWS

All the commands are to be executed on the user's machine, not on AWS.

## Install

```bash
# virtualenv venv -p python3
# source venv/bin/activate
pip install awscli boto3 requests # With Python 3
```

## Configure

cf. http://docs.aws.amazon.com/fr_fr/cli/latest/userguide/tutorial-ec2-ubuntu.html

```bash
# Register AWS credentials
aws configure
# AWS Access Key ID: See in the AWS Educate Lab page
# AWS Secret Access Key: See in the AWS Educate Lab page
# Default region name [None]: eu-west-3
# Default output format [None]: json

# Test API request
aws ec2 describe-regions --output table

# Create SSH key-pair and security groups
# Note: edit the bottom of the file to create more security groups
python configure.py
```

## Manage the machines

```bash
# Note: edit the file to launch more instances
python start.py
```

```bash
python stop.py
```

## Demo

```bash
# List running machines
python get_instances.py

# Kill a machine (fault tolerance)
python kill_machine.py <instance-id>

# Add a slave (scalability)
python add_slave.py <name> t2.<size>

# List containers
python get_services.py <instance-id>

# Kill container
python kill_service.py <instance-id> <container-id>
```
