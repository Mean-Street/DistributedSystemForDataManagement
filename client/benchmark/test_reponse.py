# -*- coding: utf-8 -*-

import datetime
import time
import requests
import sys

addrServ = sys.argv[1]

def http_test():
	url = 'http://' + addrServ + ':8080/test'

	data = {
        'begin': '2018-01-01 02',
        'end': '2018-01-01 03',
    }

	begin = datetime.datetime.now()
	res = requests.post(url, data)
	end = datetime.datetime.now()
	
	duration = end - begin
	print duration

def main():
	# 20 requêtes
	resOK = 0
	for i in range(0, 20):
		http_test()
		time.sleep(0.05) # every 1 second submit http requests

if __name__ == "__main__":
    main()