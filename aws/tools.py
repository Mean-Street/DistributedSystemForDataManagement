import config as cfg


def is_smack_instance(instance):
    for tag in instance['Tags']:
        if tag['Key'] == cfg.PROJECT_TAG_KEY and tag['Value'] == cfg.PROJECT_TAG_VALUE:
            return True
    return False
