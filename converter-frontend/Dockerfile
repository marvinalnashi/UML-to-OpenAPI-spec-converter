# Use a Node.js image to build and serve the Angular application
FROM node:21

# Set the working directory in the container
WORKDIR /app

# Copy the package.json and package-lock.json to install dependencies
COPY UMLtoOpenAPISpec/package.json UMLtoOpenAPISpec/package-lock.json ./

# Install all dependencies including Express
RUN npm install

# Copy the rest of the application's source code
COPY UMLtoOpenAPISpec/ ./

# Build the Angular application
RUN npm run build

# Install Express to serve the built application
# Note: This step is redundant if Express is already listed in package.json dependencies and installed in the previous step
RUN npm install express --save

# Copy or create a server.js file to serve the Angular application using Express
# Note: This assumes server.js is already part of the copied source files and sets up Express to serve the static files

# Expose the port the Express server will run on
EXPOSE 80

# Command to run the Express server
CMD ["node", "server.js"]
