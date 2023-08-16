FROM eclipse-temurin:17-jdk-jammy

WORKDIR /spring

COPY gradle/ gradle
COPY gradlew build.gradle settings.gradle ./
RUN chmod +x ./gradlew
RUN ./gradlew dependencies --refresh-dependencies

COPY src/ ./src
COPY spring-prod-entrypoint.sh ./
EXPOSE 8080

ENTRYPOINT bash -c "chmod +x ./gradlew && ./gradlew bootRun --args='--spring.profiles.active=dev'"
