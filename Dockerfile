FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar
COPY docker-context/application.yml application.yml

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.yml"]
