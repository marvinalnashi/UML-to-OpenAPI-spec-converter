# Use the official Node.js 14 image as a parent image
FROM node:14

# Set the working directory
WORKDIR /app

# Install Prism CLI globally
RUN npm install -g @stoplight/prism-cli

# Copy the OpenAPI spec file to the container
COPY ./converter-backend/export.yml /app/export.yml

# Expose the port Prism will run on
EXPOSE 4010

# Command to run Prism mock server
CMD ["prism", "mock", "-h", "0.0.0.0", "/app/export.yml"]
