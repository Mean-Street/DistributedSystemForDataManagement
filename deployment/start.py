def print_title(text):
    print("\n##########################")
    print(text)
    print("##########################\n")

if __name__ == "__main__":
    import time 
    from configure_masters import configure_masters
    from configure_slaves import configure_slaves
    from launch_instances import run_instance
    from start_services import start_services
    import config as cfg
    from tools import init_logs

    init_logs()

    print_title("Starting instances...")
    run_instance('master 1', 't2.small', True, [cfg.GLOBAL_SECURITY_GROUP, cfg.MESOS_SECURITY_GROUP, cfg.MARATHON_SECURITY_GROUP])
    run_instance('master 2', 't2.small', True, [cfg.GLOBAL_SECURITY_GROUP, cfg.MESOS_SECURITY_GROUP, cfg.MARATHON_SECURITY_GROUP])
    run_instance('master 3', 't2.small', True, [cfg.GLOBAL_SECURITY_GROUP, cfg.MESOS_SECURITY_GROUP, cfg.MARATHON_SECURITY_GROUP])
    run_instance('slave 1', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 2', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 3', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 4', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 5', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 6', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 7', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 8', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])
    run_instance('slave 9', 't2.small', False, [cfg.GLOBAL_SECURITY_GROUP, cfg.CLIENT_SECURITY_GROUP])

    print_title("Waiting {0}s for instances to start...".format(cfg.EC2_STARTING_DURATION))
    time.sleep(cfg.EC2_STARTING_DURATION)

    print_title("Configuring masters...")
    configure_masters()
    print_title("Configuring slaves...")
    configure_slaves()

    print_title("Launching services...")
    start_services()
