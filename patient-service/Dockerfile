FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/patient-service-0.0.1-SNAPSHOT.jar patient-service.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "patient-service.jar"]
