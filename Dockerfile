FROM eclipse-temurin:22-jdk-alpine

WORKDIR /app

# JAR 복사만 수행 (application.yml은 빌드 context에 이미 포함돼 있음)
COPY build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/app/application.yml"]
