FROM gradle:8.11-jdk21 AS build
WORKDIR /app

COPY gradle gradle
COPY gradlew .
COPY gradle.properties build.gradle.kts settings.gradle.kts ./
RUN ./gradlew dependencies

COPY . .
RUN ./gradlew shadowJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app

COPY --from=build /app/build/libs/*-all.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
