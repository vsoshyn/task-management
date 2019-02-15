#!/usr/bin/env sh

export JAVA_HOME=/usr/lib/java/jdk-11
export PATH=$PATH:$JAVA_HOME/bin

cd ..

docker-compose -f environment/prod/docker-compose.yml down
docker-compose -f environment/prod/docker-compose.yml up -d postgres

sh gradlew clean bootDistTar -x test
sh gradlew update -PrunList=prod

docker-compose -f environment/prod/docker-compose.yml up --build -d task-manager