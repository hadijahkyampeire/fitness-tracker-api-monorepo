# ------------ STAGE 1: Build -------------
#FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
#WORKDIR /app
#COPY . .
#RUN mvn clean package -pl workout_service -am -DskipTests
#
## ------------ STAGE 2: Runtime -------------
#FROM eclipse-temurin:21-alpine
#WORKDIR /app
#COPY --from=builder /app/workout_service/target/workout_service-0.0.1-SNAPSHOT.jar app.jar
#EXPOSE 8082
#ENTRYPOINT ["java", "-jar", "app.jar"]
