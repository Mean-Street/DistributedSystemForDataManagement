import os
import boto3
from botocore.exceptions import ClientError

ec2 = boto3.client('ec2')


def get_default_cidr_block():
    vpcs = ec2.describe_vpcs()['Vpcs']
    for vpc in vpcs:
        if vpc['IsDefault']:
            return vpc['CidrBlock']


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


def create_security_group(name, description, ports, cidr):
    try:
        resp = ec2.create_security_group(GroupName=name, Description=description)
    except ClientError as e:
        print(e)
        return

    print("Security group '{0}' created.".format(name))
    group_id = resp['GroupId']
    ports = set(ports)
    permissions = []
    for port in ports:
        if isinstance(port, int):
            from_port = to_port = port
        else:
            from_port, to_port = port
        permissions.append({
            'IpProtocol': 'tcp',
            'FromPort': from_port,
            'ToPort': to_port,
            'IpRanges': [{'CidrIp': cidr}]
        })
    permissions.append({
        'IpProtocol': 'tcp',
        'FromPort': 22,
        'ToPort': 22,
        'IpRanges': [{'CidrIp': '0.0.0.0/0'}]
    })

    try:
        ec2.authorize_security_group_ingress(GroupId=group_id, IpPermissions=permissions)
    except ClientError as e:
        print(e)


if __name__ == '__main__':
    import config as cfg

    create_key_pair(cfg.KEY_PAIR_NAME, cfg.KEY_PAIR_PATH)

    # We open all ports on the private network for Marathon to work
    create_security_group(cfg.GLOBAL_SECURITY_GROUP, "Global", [(0, 65535)], get_default_cidr_block())
    create_security_group(cfg.MESOS_SECURITY_GROUP, "Mesos", [5050], '0.0.0.0/0')
    create_security_group(cfg.MARATHON_SECURITY_GROUP, "Marathon", [8080, 9090], '0.0.0.0/0')
    create_security_group(cfg.CLIENT_SECURITY_GROUP, "Client", [8080], '0.0.0.0/0')
