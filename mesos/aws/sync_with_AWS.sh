#! /bin/sh
SLAVE_PUBLIC_IP=52.88.167.87
MASTER_PUBLIC_IP=52.33.64.166
IP2=52.11.199.130
IP3=52.36.251.4

rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${MASTER_PUBLIC_IP}:~/SDTD
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${SLAVE_PUBLIC_IP}:~/SDTD
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${IP2}:~/SDTD
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" ../.. ubuntu@${IP3}:~/SDTD
