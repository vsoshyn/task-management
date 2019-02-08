FROM openjdk:11-jre-slim

WORKDIR /home

RUN apt-get update && \
    apt-get -y install netcat && \
    apt-get -y install tar

ADD build/distributions/backend-boot.tar task-management/
ADD build/resources/main/src/main/sql task-management/sql/
ADD wait-for.sh .
ADD https://github.com/liquibase/liquibase/releases/download/liquibase-parent-3.6.3/liquibase-3.6.3-bin.tar.gz /tmp
COPY dependencies/postgresql-42.2.2.jar /opt/liquibase/lib/

RUN tar -xzf /tmp/liquibase-3.6.3-bin.tar.gz -C /opt/liquibase/ && \
    rm /tmp/liquibase-3.6.3-bin.tar.gz

ENV JAVA_OPTS="-Dspring.profiles.active=prod"

WORKDIR /home/task-manager/sql

RUN sh -c '/home/wait-for.sh postgres:5432 -t 5'
RUN sh -c '/opt/liquibase/liquibase --changeLogFile=changelog.yml --url=jdbc:postgresql://postgres:5432/task-manager --driver=org.postgresql.Driver --username=task-manager --password=12345678 update'

CMD sh task-management/backend-boot/bin/backend

# docker-compose up -d postgres
# sh gradlew update -PrunList=test
# gradlew bootDistTar
# docker build -t task-management .
# docker-compose up -d
