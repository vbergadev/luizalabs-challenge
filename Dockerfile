FROM openjdk:17-jdk-alpine
MAINTAINER baeldung.com
COPY target/**.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]