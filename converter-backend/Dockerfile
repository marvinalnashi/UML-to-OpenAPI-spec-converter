# Assuming you're using a Maven project
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app

# Copy your entire project into the Docker image, including the resources directory
COPY converter-backend /app

# Run Maven to build your project
RUN mvn clean package

# Use OpenJDK to run your built application
FROM openjdk:17
WORKDIR /app

# Copy the built application from the build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Expose the port your application uses
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "app.jar"]
