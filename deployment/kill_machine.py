import boto3
from get_instances import get_instances, get_id, is_id_valid

ec2 = boto3.client('ec2')


def kill(id_):
    ec2.terminate_instances(InstanceIds=[id_])


if __name__ == "__main__":
    import sys
    try:
        id_ = sys.argv[1]
        assert is_id_valid(id_)
    except (AssertionError, IndexError):
        print("Please specify a valid instance id")
        sys.exit(1)
    kill(id_)
