FROM jetbrains/teamcity-agent:2018.1.1
RUN apt update && \
    apt -y full-upgrade && \
    apt install wget && \
    wget 'https://download.java.net/java/GA/jdk11/9/GPL/openjdk-11.0.2_linux-x64_bin.tar.gz' -O /tmp/jdk-11.tar.gz && \
    mkdir -p /usr/lib/jvm && \
    tar -xvf /tmp/jdk-11.tar.gz -C /usr/lib/jvm && \
    rm /tmp/jdk-11.tar.gz && \
    apt -y autoremove