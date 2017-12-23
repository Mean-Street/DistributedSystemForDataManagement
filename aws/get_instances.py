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


if __name__ == '__main__':
    print(get_instances())
