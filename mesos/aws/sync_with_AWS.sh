#! /bin/sh
IP0=52.88.167.87
IP1=52.33.64.166
IP2=52.11.199.130
IP3=52.36.251.4
IP4=34.212.245.184

rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${IP0}:~/SDTD
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${IP1}:~/SDTD
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${IP2}:~/SDTD
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${IP3}:~/SDTD
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${IP4}:~/SDTD
