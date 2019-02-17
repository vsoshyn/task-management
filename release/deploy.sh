#!/usr/bin/env sh

cd ..

TAG=$1

git reset --hard HEAD
git fetch -t
git checkout ${TAG}

docker-compose -f environment/prod/docker-compose.yml down
docker-compose -f environment/prod/docker-compose.yml up -d postgres
gradle update -PrunList=prod
docker-compose -f environment/prod/docker-compose.yml up -d