# Use a multi-stage build to first build the Java application
# Stage 1: Build the application
FROM maven:3.8.4-openjdk-17 as builder

# Copy the source code
COPY ./converter-backend /build
WORKDIR /build

# Package the application
RUN mvn clean package

# Stage 2: Setup the runtime container
FROM openjdk:17-slim-buster

# Install necessary packages for fetching Docker's GPG key and adding the Docker repository
RUN apt-get update && \
    apt-get install -y \
    apt-transport-https \
    ca-certificates \
    curl \
    gnupg-agent \
    software-properties-common

# Add Docker’s official GPG key
RUN curl -fsSL https://download.docker.com/linux/debian/gpg | apt-key add -

# Set up the Docker stable repository
RUN add-apt-repository \
   "deb [arch=amd64] https://download.docker.com/linux/debian \
   $(lsb_release -cs) \
   stable"

# Install Docker Engine
RUN apt-get update && \
    apt-get install -y docker-ce docker-ce-cli containerd.io

# Set the working directory to /app
WORKDIR /app

# Copy the built JAR file from the builder stage
COPY --from=builder /build/target/*.jar /app/app.jar

# Expose the port your application uses
EXPOSE 8080

# Command to run your application
CMD ["java", "-jar", "/app/app.jar"]
