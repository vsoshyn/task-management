#!/bin/sh

/home/wait-for.sh postgres:5431 -t 5
# add here failing if not reachable
/home/task-management/backend-boot/bin/backend