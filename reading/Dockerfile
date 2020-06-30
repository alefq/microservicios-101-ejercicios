#
# Package stage
#
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} reading.jar
ENTRYPOINT ["java","-jar","reading.jar"]