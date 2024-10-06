FROM eclipse-temurin:latest
WORKDIR /app

# Copy the jar file into the container
COPY target/*.jar app.jar

EXPOSE 5000

# Specify the command to run on container start
ENTRYPOINT ["java", "-jar", "app.jar"]