if __name__ == "__main__":
    import sys
    import time
    import config as cfg
    from launch_instances import run_instance
    from configure_slaves import configure_slave
    from get_instances import get_from_id, get_id

    try:
        name = sys.argv[1]
        instance_size = sys.argv[2]
    except IndexError:
        print("Usage: python3 add_slave.py name instance-size")
        print('Example: python3 add_slave.py "Slave 666" t2.small')
        sys.exit(1)

    instance = run_instance(name, instance_size, False, [cfg.GLOBAL_SECURITY_GROUP])

    print("Waiting {0}s for instance to start...".format(cfg.EC2_STARTING_DURATION))
    time.sleep(cfg.EC2_STARTING_DURATION)

    print("Configuring slave...")
    instance = get_from_id(get_id(instance)) 
    configure_slave(instance)
