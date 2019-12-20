
FROM openjdk:8-jdk-alpine

RUN apk add --no-cache apache-ant
RUN mkdir -p /checker/tasks
ADD . /checker/tasks

USER nobody
WORKDIR /checker/tasks




RUN \
    cd /usr/local && \
    curl -L https://services.gradle.org/distributions/gradle-2.5-bin.zip -o gradle-2.5-bin.zip && \
    unzip gradle-2.5-bin.zip && \
    rm gradle-2.5-bin.zip

# Export some environment variables
ENV GRADLE_HOME=/usr/local/gradle-2.5
ENV PATH=$PATH:$GRADLE_HOME/bin JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

# Define default command.
CMD ["bash"]




ENTRYPOINT ["/usr/bin/gradle"]
