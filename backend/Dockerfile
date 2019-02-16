FROM openjdk:11-jre-slim

WORKDIR /home

RUN apt-get update && \
    apt-get -y install netcat

ADD build/distributions/backend-boot-*.tar task-management/
ADD wait-for.sh .
ADD start.sh .

ENV JAVA_OPTS="-Dspring.profiles.active=prod"

EXPOSE 3002

ENTRYPOINT ["sh", "/home/start.sh"]

# docker-compose up -d postgres
# sh gradlew update -PrunList=test
# gradlew bootDistTar
# docker build -t task-management .
# docker-compose up -d
