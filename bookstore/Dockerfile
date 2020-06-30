#
# Package stage
#
FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY ${JAR_FILE} bookstore.jar
ENTRYPOINT ["java","-jar","bookstore.jar"]