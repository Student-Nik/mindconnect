# Stage 1: Build the Spring Boot application
FROM maven:3.9.3-eclipse-temurin-17 AS builder
WORKDIR /app

# Copy Maven configuration files
COPY pom.xml .
COPY src ./src

# Build the Spring Boot application (skip tests to speed up build)
RUN mvn clean package -DskipTests

# Stage 2: Create the final, lightweight runtime image
FROM eclipse-temurin:17-jre-focal
WORKDIR /app

# Copy the built jar from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port used by Spring Boot
EXPOSE 9090

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
 
