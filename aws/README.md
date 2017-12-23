# AWS

All the commands are to be executed on the user's machine, not on AWS.

## Install

```bash
# Can be launched in a virtualenv
pip install boto3
```

## Configure

cf. http://docs.aws.amazon.com/fr_fr/cli/latest/userguide/tutorial-ec2-ubuntu.html

```bash
# Register AWS credentials
aws configure
# AWS Access Key ID: See in the AWS Educate Lab page
# AWS Secret Access Key: See in the AWS Educate Lab page
# Default region name [None]: eu-west-2
# Default output format [None]: json

# Test API request
aws ec2 describe-regions --output table

# Configure
python configure.py
```

## Launch the machines

```bash
./launch-instances.sh
```
