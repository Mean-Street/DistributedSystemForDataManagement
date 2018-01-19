#!/bin/sh

for i in {1..20}
do
    python dos.py $1 &
done > 'res_dos'