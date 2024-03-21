# Stage 1: Build the Angular application
FROM node:21 AS build
WORKDIR /app

# Copy the project files
COPY UMLtoOpenAPISpec/package.json ./
COPY UMLtoOpenAPISpec/package-lock.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application's source code
COPY UMLtoOpenAPISpec/ ./

# Build the project
RUN npm run build

# Stage 2: Serve the application with Nginx
# Stage 2: Serve the application with Nginx
FROM nginx:alpine
COPY --from=build /app/dist/umlto-open-apispec /usr/share/nginx/html
COPY UMLtoOpenAPISpec/default.conf /etc/nginx/conf.d/default.conf

# Expose port 80 for the Nginx server
EXPOSE 80

# Start Nginx and keep it running in the foreground
CMD ["nginx", "-g", "daemon off;"]
