#!/usr/bin/env sh

cd ..

gradle clean bootDistTar -x test
docker build -t task-manager backend
docker-compose -f environment/prod/docker-compose.yml down
docker-compose -f environment/prod/docker-compose.yml up -d postgres
gradle update -PrunList=prod
docker-compose -f environment/prod/docker-compose.yml up -d