#!/bin/sh

for i in {1..10}
do
    #echo "Requests ($i) begin"
    python test_reponse.py $1 &
done > 'res_reponse'