import os
import boto3
import config as cfg

ec2 = boto3.client('ec2')


def run_instance(name, type_, security_groups):
    ec2.run_instances(
        ImageId=cfg.UBUNTU_IMAGE_ID,
        InstanceType=type_,
        KeyName=cfg.KEY_PAIR_NAME,
        MinCount=1,
        MaxCount=1,
        SecurityGroupIds=security_groups,
        TagSpecifications=[{
            'ResourceType': 'instance',
            'Tags': [
                {
                    'Key': 'Name',
                    'Value': name
                },
                {
                    'Key': cfg.PROJECT_TAG_KEY,
                    'Value': cfg.PROJECT_TAG_VALUE
                },
            ]
        }]
    )


if __name__ == '__main__':
    run_instance('zookeeper', 't2.micro', [cfg.ZOOKEEPER_SECURITY_GROUP])
