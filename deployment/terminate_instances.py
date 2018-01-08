import boto3
import config as cfg
from tools import is_smack_instance

ec2 = boto3.client('ec2')


def terminate_instances():
    to_terminate = []
    resp = ec2.describe_instances()
    reservations = resp['Reservations']
    for reservation in reservations:
        for instance in reservation['Instances']:
            if is_smack_instance(instance):
                to_terminate.append(instance['InstanceId'])

    ec2.terminate_instances(InstanceIds=to_terminate)


if __name__ == '__main__':
    terminate_instances()
