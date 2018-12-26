FROM openjdk:11-jre-slim

WORKDIR /home

ADD build/distributions/backend-boot.tar task-management/

ENV JAVA_OPTS="-Dspring.profiles.active=prod"

CMD sh task-management/backend-boot/bin/backend

# docker-compose up -d postgres
# sh gradlew update -PrunList=test
# gradlew bootDistTar
# docker build -t task-management .
# docker-compose up -d
