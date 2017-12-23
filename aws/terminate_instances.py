import os
import boto3
import config as cfg

ec2 = boto3.client('ec2')


def is_smack_instance(instance):
    for tag in instance['Tags']:
        if tag['Key'] == cfg.PROJECT_TAG_KEY and tag['Value'] == cfg.PROJECT_TAG_VALUE:
            return True
    return False


def terminate_instances():
    to_terminate = []
    resp = ec2.describe_instances()
    reservations = resp['Reservations']
    for reservation in reservations:
        for instance in reservation['Instances']:
            if is_smack_instance(instance):
                to_terminate.append(instance['InstanceId'])

    print(to_terminate)
    ec2.terminate_instances(InstanceIds=to_terminate)


if __name__ == '__main__':
    terminate_instances()
