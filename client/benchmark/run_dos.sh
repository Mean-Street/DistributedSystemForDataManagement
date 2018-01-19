#!/bin/sh

for i in {1..100}
do
    #echo "Requests ($i) begin"
    python dos.py &
done > 'res_dos'