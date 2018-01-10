import os
import subprocess as sp
import config as cfg

LOG_PATH = "logs.txt"


def init_logs():
    open(LOG_PATH, "w").close()


def is_smack_instance(instance):
    if 'Tags' not in instance:
        return False
    for tag in instance['Tags']:
        if tag['Key'] == cfg.PROJECT_TAG_KEY and tag['Value'] == cfg.PROJECT_TAG_VALUE:
            return True
    return False


def ssh(ip, cmd, shell=False):
    cmd = "ssh -i " + cfg.KEY_PAIR_PATH + " -o StrictHostKeyChecking=no " + cfg.UBUNTU_USER + "@" + ip + " " + cmd

    with open(LOG_PATH, "a") as logfile:
        logfile.write(cmd + "\n")

    if not shell:
        p = sp.Popen(cmd.split())
    else:
        p = sp.Popen(cmd, shell=True)
    return p
