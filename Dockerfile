# Use a minimal distroless base image for the runtime
FROM gcr.io/distroless/java17-debian11

# Set the working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/travel-be-service-0.0.1-SNAPSHOT.jar /app/app.jar

# Run the application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
