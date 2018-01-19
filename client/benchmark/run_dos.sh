#!/bin/sh

for i in {1..20}
do
    python dos.py &
done > 'res_dos'