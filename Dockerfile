FROM openjdk:11

MAINTAINER Dheeraj Agrawal <agrawal.dheeraj.7@gmail.com>

COPY build/libs/spring-boot-admin-server-*.jar /opt/spring-boot-admin-docker/app.jar

EXPOSE 9090

WORKDIR /opt/spring-boot-admin-docker

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
