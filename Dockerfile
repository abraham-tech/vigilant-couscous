# Use an official OpenJDK image
FROM openjdk:21-jdk-slim
# Set the working directory inside the container
WORKDIR /app
# Copy the jar file to the container. Check target folder to get the name, <artifactId>-0.0.1....
COPY target/springboottest-0.0.1-SNAPSHOT.jar app.jar
# Expose the port the app runs on
EXPOSE 8081
# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]