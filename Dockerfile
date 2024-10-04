# Stage 1: Build stage
FROM maven:3.8.7-openjdk-17-slim AS build
LABEL authors="rohi"

# Set working directory in container
WORKDIR /app

# Copy pom.xml and download dependencies before copying the entire source to leverage Docker caching
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the entire source code
COPY . .

# Build the project
RUN mvn clean package -DskipTests

# Stage 2: Production stage
FROM openjdk:17-slim

# Set working directory in the production container
WORKDIR /app

# Copy the JAR file from the build stage to the production stage
COPY --from=build /app/target/b3-1.jar /app/b3-1.jar

# Expose the application port
EXPOSE 8080

# Command to run the JAR file
ENTRYPOINT ["java", "-jar", "b3-1.jar"]
