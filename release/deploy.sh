#!/usr/bin/env sh

docker-compose -f environment/prod/docker-compose.yml down
docker-compose -f environment/prod/docker-compose.yml up -d postgres
gradle update -PrunList=prod
docker-compose -f environment/prod/docker-compose.yml up -d