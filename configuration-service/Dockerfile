#
# Package stage
#
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} configuration-server.jar
ENTRYPOINT ["java","-jar","configuration-server.jar"]