# Use an official Maven image as the base image
FROM maven:3.8.4-openjdk-17-slim AS build

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use an official OpenJDK image as the base image
FROM eclipse-temurin

# Set the working directory in the container
WORKDIR /app

# Copia el archivo JAR generado
COPY --from=build /app/target/simple-cache-proxy-1.0-SNAPSHOT.jar proxy.jar

# Define el comando de entrada
ENTRYPOINT ["java", "-jar", "proxy.jar"]