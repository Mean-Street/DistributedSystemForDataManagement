#scp -i TestMesos.pem -o StrictHostKeyChecking=no -r . ubuntu@52.33.64.166:~
rsync -Pav -e "ssh -i TestMesos.pem -o StrictHostKeyChecking=no" . ubuntu@52.33.64.166:~
