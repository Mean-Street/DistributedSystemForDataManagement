import boto3
import config as cfg
from tools import is_smack_instance

ec2 = boto3.client('ec2')


def has_state(instance, state):
    return instance['State']['Name'] == state


def get_instances(state='running', is_slave=None):
    instances = []
    
    resp = ec2.describe_instances()
    reservations = resp['Reservations']
    for reservation in reservations:
        for instance in reservation['Instances']:
            if not is_smack_instance(instance) or is_master is not None and is_master(instance) == is_slave:
                continue
            if has_state(instance, state):
                instances.append(instance)

    return instances


def is_id_valid(id_):
    for instance in get_instances():
        if get_id(instance) == id_:
            return True
    return False


def get_from_id(id_):
    resp = ec2.describe_instances(InstanceIds=[id_])
    return resp['Reservations'][0]['Instances'][0]


def get_private_ip(instance):
    return instance['PrivateIpAddress']


def get_public_ip(instance):
    return instance['PublicIpAddress']


def get_id(instance):
    return instance['InstanceId']


def get_name(instance):
    tags = instance['Tags']
    for tag in tags:
        if tag['Key'] == 'Name':
            return tag['Value']
    return None


def is_master(instance):
    tags = instance['Tags']
    for tag in tags:
        if tag['Key'] == cfg.MASTER_TAG_KEY:
            return tag['Value'] == cfg.MASTER_TAG_VALUE
    return False


if __name__ == '__main__':
    instances = get_instances()
    print("id | name | public_ip | private_ip")
    print("----------------------------------------------------")
    for instance in instances:
        print(get_id(instance), get_name(instance), get_public_ip(instance), get_private_ip(instance), sep=" | ")
