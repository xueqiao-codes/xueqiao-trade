#!/bin/bash

while :
do
	cp -f /root/.ssh/auth/authorized_keys /root/.ssh/authorized_keys
	chmod 600 /root/.ssh/authorized_keys
	sleep 30s
done