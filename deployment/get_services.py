from get_instances import get_instances, get_from_id, is_id_valid, get_public_ip
from tools import ssh


def get_services(instance):
    p = ssh(get_public_ip(instance), "sudo docker ps") 
    p.wait()


if __name__ == "__main__":
    import sys
    try:
        id_ = sys.argv[1]
        assert is_id_valid(id_)
    except (AssertionError, IndexError):
        print("Please specify a valid instance id")
        sys.exit(1)
    get_services(get_from_id(id_))
