FROM openjdk:8-jre-alpine
#ARG JAR_FILE=build/libs/Mailbox-0.0.1-SNAPSHOT.jar
COPY build/libs/Mailbox-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]