FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/medical-record-service-0.0.1-SNAPSHOT.jar medical-record-service.jar
EXPOSE 8181
ENTRYPOINT ["java", "-jar", "medical-record-service.jar"]
