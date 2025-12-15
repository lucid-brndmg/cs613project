
FROM gradle:8.14-jdk17 AS build

WORKDIR /app


COPY build.gradle settings.gradle ./
COPY gradle ./gradle


RUN gradle dependencies --no-daemon || true


COPY src ./src


RUN gradle bootJar --no-daemon


FROM eclipse-temurin:17-jre-jammy

WORKDIR /app


COPY --from=build /app/build/libs/*.jar app.jar


EXPOSE 8080


HEALTHCHECK --interval=30s --timeout=3s --start-period=40s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1


ENTRYPOINT ["java", "-jar", "app.jar"]
