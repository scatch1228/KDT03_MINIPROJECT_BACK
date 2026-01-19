# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

# 1. Copy the wrapper files first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# 2. ADD THIS LINE: Grant execute permission to the wrapper
#RUN chmod +x mvnw

# 3. (Optional but recommended) Fix Windows line endings
RUN tr -d '\r' < mvnw > mvnw_unix && mv mvnw_unix mvnw

# 4. Copy source and build
COPY src src
#RUN ./mvnw install -DskipTests

# RUN WITH SH: This is the most reliable way to bypass permission issues
RUN sh mvnw install -DskipTests

# Use a slim runtime stage
# Stage 2: Runtime
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
# We use a specific glob pattern to ensure we get the correct executable JAR
COPY --from=build /workspace/app/target/*[0-9].jar app.jar

# Render uses the PORT environment variable; Spring Boot picks this up automatically
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]