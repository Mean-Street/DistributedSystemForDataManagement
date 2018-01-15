import requests
import json
import matplotlib.pyplot as plt
import sys

if len(sys.argv) != 2:
    print("Usage: python3 visualizer.py <host>")
    exit()

host = sys.argv[1]
headers = {'content-type': 'application/json'}
payload = {'begin': '2018-01-01 01', 'end': '2018-01-01 22'}

r = requests.post('http://' + host + ':8080/weather',
                  data=json.dumps(payload),
                  headers=headers)

raw_data = json.loads(r.text)['res']
temperatures = [e['temperature'] for e in raw_data]
sentiments = [e['feeling'] for e in raw_data]

plt.plot(temperatures, sentiments, 'ro')
plt.xlabel('temperature (ºC)')
plt.ylabel('sentiment')
plt.show()
