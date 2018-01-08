import subprocess as sp
import config as cfg


def is_smack_instance(instance):
    if 'Tags' not in instance:
        return False
    for tag in instance['Tags']:
        if tag['Key'] == cfg.PROJECT_TAG_KEY and tag['Value'] == cfg.PROJECT_TAG_VALUE:
            return True
    return False


def ssh(ip, cmd, blocking=True):
    p = sp.Popen(["ssh", "-i", cfg.KEY_PAIR_PATH, "-o", "StrictHostKeyChecking=no", cfg.UBUNTU_USER + "@" + ip] + cmd.split())
    if blocking:
        p.wait()
    return p
