import os
import boto3
from botocore.exceptions import ClientError

ec2 = boto3.client('ec2')


def create_key_pair(name, path):
    try:
        key_pair = ec2.create_key_pair(KeyName=name)
    except ClientError as e:
        print(e)
        return
    try:
        os.remove(path)
    except OSError:
        pass
    with open(path, "w") as f:
        f.write(key_pair["KeyMaterial"])
    os.chmod(path, 0o400)
    print("The key '{0}' was created and saved at {1}.".format(name, path))


def create_security_group(name, description, ports):
    try:
        resp = ec2.create_security_group(GroupName=name, Description=description)
    except ClientError as e:
        print(e)
        return

    print("Security group '{0}' created.".format(name))
    group_id = resp['GroupId']
    ports = set(ports)
    ports.add(22)
    permissions = [{
        'IpProtocol': 'tcp',
        'FromPort': port,
        'ToPort': port,
        'IpRanges': [{'CidrIp': '0.0.0.0/0'}]
    } for port in ports]

    try:
        ec2.authorize_security_group_ingress(GroupId=group_id, IpPermissions=permissions)
    except ClientError as e:
        print(e)


if __name__ == '__main__':
    import config as cfg

    create_key_pair(cfg.KEY_PAIR_NAME, cfg.KEY_PAIR_PATH)

    create_security_group(cfg.ZOOKEEPER_SECURITY_GROUP, "Zookeeper", [2181])
    create_security_group(cfg.KAFKA_SECURITY_GROUP, "Kafka", [9092])
    create_security_group(cfg.CASSANDRA_SECURITY_GROUP, "Cassandra", [9042, 7000, 7001])
    create_security_group(cfg.MESOS_MASTER_SECURITY_GROUP, "Mesos Master", [5050])
