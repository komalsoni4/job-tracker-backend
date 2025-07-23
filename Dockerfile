# ------------ Build Stage ------------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy all project files
COPY . .

# Build the application (skipping tests for faster build)
RUN ./mvnw clean package -DskipTests

# ------------ Run Stage ------------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy the generated jar from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 9090

# Optional: Set upload directory environment variable
ENV UPLOAD_DIR=/uploads

# Create upload dir inside container
RUN mkdir -p /uploads

# Start the application
ENTRYPOINT ["java", "-jar", "app.jar"]
