from terminate_instances import terminate_instances
from stop_services import stop_services
from get_instances import get_instances, get_public_ip, is_master


def print_header(text):
    print("\n")
    print("-------------------")
    print(text)
    print("-------------------")
    print("\n")


if __name__ == "__main__":
    print_header("Stopping services...")
    stop_services()

    print_header("Shutting down machines...")
    terminate_instances()
