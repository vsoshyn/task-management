#!/usr/bin/env sh

sh /home/wait-for.sh postgres:5431 -t 5
# add here failing if not reachable
sh "/home/task-management/$( ls /home/task-management )/bin/backend"