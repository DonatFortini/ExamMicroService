FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/appointment-service-0.0.1-SNAPSHOT.jar appointment-service.jar
EXPOSE 8095
ENTRYPOINT ["java", "-jar", "appointment-service.jar"]
