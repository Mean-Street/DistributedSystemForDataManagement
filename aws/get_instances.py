import boto3
import config as cfg
from tools import is_smack_instance

ec2 = boto3.client('ec2')


def has_state(instance, state):
    return instance['State']['Name'] == state


def get_instances(state='running'):
    instances = []
    
    resp = ec2.describe_instances()
    reservations = resp['Reservations']
    for reservation in reservations:
        for instance in reservation['Instances']:
            if is_smack_instance(instance) and has_state(instance, state):
                instances.append(instance)

    return instances


def get_private_ip(instance):
    return instance['PrivateIpAddress']


def get_public_ip(instance):
    return instance['PublicIpAddress']


def get_name(instance):
    tags = instance['Tags']
    for tag in tags:
        if tag['Key'] == 'Name':
            return tag['Value']
    return None


if __name__ == '__main__':
    instances = get_instances()
    print(instances, end='\n\n')
    print('First instance name:', get_name(instances[0]))
