# AWS

All the commands are to be executed on the user's machine, not on AWS.

## Install

```bash
# Can be launched in a virtualenv
# virtualenv venv
# source venv/bin/activate
# With Python 3
pip install awscli
pip install boto3
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

### Start instances

```bash
# Note: edit the bottom of the file to launch more instances
python launch_instances.py

# Wait a little for the instances to start...

python configure_masters.py
python configure_slaves.py
```

### Terminate all instances

```bash
python terminate_instances.py
```
