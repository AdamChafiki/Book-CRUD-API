# Step 1: Use a lightweight Java runtime
FROM eclipse-temurin:21-jdk-alpine

# Step 2: Set the working directory inside the container
WORKDIR /app

# Step 3: Copy the JAR from your project into the container
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar

# Step 4: Expose the port your app will run on
EXPOSE 8080

# Step 5: Run the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
