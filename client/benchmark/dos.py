# -*- coding: utf-8 -*-

import datetime
import time
import requests

addrServ = 'localhost'

def http_test():
	url = 'http://' + addrServ + ':8080/example'

	res = requests.get(url)
	if res.text == u'test réussi\n':
		return 1
	return 0

def main():
	# 20 requêtes
	resOK = 0
	for i in range(0, 20):
		resOK += http_test()
		time.sleep(0.05) # every 1 second submit http requests
	print str(resOK) + '/20'

if __name__ == "__main__":
    main()