#!/usr/bin/env sh

git checkout master
git merge develop

git push --all git@github.com:vsoshyn/task-management.git
git push --tags git@github.com:vsoshyn/task-management.git