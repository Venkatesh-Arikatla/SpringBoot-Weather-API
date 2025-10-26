# Use OpenJDK 21 instead of 17
FROM openjdk:21-jdk-slim

# Set working directory
WORKDIR /app

# Copy the entire project
COPY . .

# Make gradlew executable
RUN chmod +x ./gradlew

# Build the application
RUN ./gradlew clean build -x test

# Expose port (Render will override this)
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "build/libs/springboot-demo-0.0.1-SNAPSHOT.jar"]