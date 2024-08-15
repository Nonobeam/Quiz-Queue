# Use an official Maven image to build the application with OpenJDK 21
FROM maven:3.9-sapmachine-21 AS build

# Set the working directory
WORKDIR /app

# Copy the module POM file and source code
COPY pom.xml .
COPY src ./src

# Run the Maven build
RUN mvn clean package

# Use an official OpenJDK 21 runtime as a parent image
FROM amazoncorretto:21-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/Configuration-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the application runs on
EXPOSE 8888

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]