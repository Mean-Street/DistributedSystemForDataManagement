import boto3
import config as cfg
from tools import is_smack_instance
from get_instances import get_instances, get_id

ec2 = boto3.client('ec2')


def terminate_instances():
    to_terminate = [get_id(instance) for instance in get_instances()]
    if to_terminate:
        ec2.terminate_instances(InstanceIds=to_terminate)
    print("{0} instances terminated.".format(len(to_terminate)))


if __name__ == '__main__':
    terminate_instances()
