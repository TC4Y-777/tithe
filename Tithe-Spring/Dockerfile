FROM eclipse-temurin:17-jdk-jammy

WORKDIR /spring

COPY gradle/ gradle
COPY gradlew build.gradle settings.gradle ./
RUN ./gradlew dependencies --refresh-dependencies

COPY src/ ./src
EXPOSE 8080

CMD ["./gradlew", "bootRun"]
