from get_instances import get_instances, get_from_id, is_id_valid, get_public_ip
from tools import ssh


def kill_service(instance, service):
    p = ssh(get_public_ip(instance), "sudo docker stop " + service)
    p.wait()


if __name__ == "__main__":
    import sys
    try:
        instance_id = sys.argv[1]
        assert is_id_valid(instance_id)
        service = sys.argv[2]
    except (AssertionError, IndexError):
        print("Please specify a valid instance id and a service name")
        sys.exit(1)
    kill_service(get_from_id(instance_id), service)
