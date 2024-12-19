FROM openjdk:8-jdk-alpine
WORKDIR /app
COPY target/api-gateway-0.0.1-SNAPSHOT.jar api-gateway.jar
EXPOSE 8010
ENTRYPOINT ["java", "-jar", "api-gateway.jar"]
