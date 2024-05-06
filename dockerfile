# Use a slim Java 17 base image (replace with desired tag if needed)
FROM arm64v8/eclipse-temurin:17-jre

RUN mkdir /images

# Define volume mount point
VOLUME /images

# Copy PNG file to volume mount
COPY test.png /images

# Set a working directory for the application
WORKDIR /app

# Copy your JAR file to the working directory
COPY install.jar app.jar

# Expose the port your application uses (replace 8080 with the actual port)
EXPOSE 8080

# Command to run the application using Java 17
CMD ["java", "-jar", "app.jar"]