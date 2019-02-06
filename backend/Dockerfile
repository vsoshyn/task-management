FROM openjdk:11-jre-slim

WORKDIR /home

RUN apt-get update && \
    apt-get -y install netcat

ADD build/distributions/backend-boot.tar task-management/
ADD wait-for.sh .

ENV JAVA_OPTS="-Dspring.profiles.active=prod"

CMD sh -c './wait-for.sh postgres:5432 -t 5' && \
    sh task-management/backend-boot/bin/backend

# docker-compose up -d postgres
# sh gradlew update -PrunList=test
# gradlew bootDistTar
# docker build -t task-management .
# docker-compose up -d
