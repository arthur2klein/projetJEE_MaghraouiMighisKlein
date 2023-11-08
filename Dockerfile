# syntax=docker/dockerfile:1

FROM eclipse-temurin:17-jdk-jammy
# Set the directory for following commands
WORKDIR /app
# Copy the files to set up the dependencies
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
# Test DNS resolution using nslookup
RUN apt-get update && apt-get install -y dnsutils
RUN nslookup google.com
# Install the dependencies
RUN ./mvnw dependency:resolve
# Add the source code to the image
COPY src ./src
CMD ./mvnw spring-boot:run
