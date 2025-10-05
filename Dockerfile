# Use Amazon Corretto 21 JDK
FROM amazoncorretto:21-alpine

WORKDIR /app

# Copy built jar
COPY target/algo-force.jar .

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/algo-force.jar"]