# Use Maven base image with JDK 17 to compile and run the Spring Boot application
FROM maven:3.8.4-openjdk-17 AS build

# Set the working directory in the container
WORKDIR /app

# Copy the Maven configuration file
COPY converter-backend/pom.xml /app

# Copy the project source
COPY converter-backend/src /app/src

# Compile and package the application
# Skip tests to speed up the build if you don't need them during docker build
RUN mvn clean package -DskipTests

# For the actual application container, use the OpenJDK image to run the jar
# Adjusted to use a known existing OpenJDK 17 image
FROM openjdk:17-slim

WORKDIR /app

# Copy the jar file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
