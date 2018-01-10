import os
import boto3
import config as cfg

ec2 = boto3.client('ec2')


def get_image_id():
    images = ec2.describe_images(Filters=[{'Name': 'name', 'Values': [cfg.UBUNTU_IMAGE_NAME]}])
    return images['Images'][0]['ImageId']


def run_instance(name, type_, is_master, security_groups):
    print('Launching {0}...'.format(name))
    ec2.run_instances(
        ImageId=get_image_id(),
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
                {
                    'Key': cfg.MASTER_TAG_KEY,
                    'Value': cfg.MASTER_TAG_VALUE if is_master else cfg.SLAVE_TAG_VALUE
                },
            ]
        }]
    )
