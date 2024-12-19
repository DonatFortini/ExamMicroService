FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/practitioner-service-0.0.1-SNAPSHOT.jar practitioner-service.jar
EXPOSE 8090
ENTRYPOINT ["java", "-jar", "practitioner-service.jar"]
