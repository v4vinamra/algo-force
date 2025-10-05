# Stage 1: Build the JAR
FROM maven:3.9.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/target/algoforce-0.0.1-SNAPSHOT.jar .
ENTRYPOINT ["java","-jar","algoforce-0.0.1-SNAPSHOT.jar"]
