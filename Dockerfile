FROM eclipse-temurin:21-jre-alpine AS build

WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src  src

RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre-alpine
VOLUME [ "/tmp" ]
COPY --from=build /workspace/app/build/libs/*.jar app.jar
EXPOSE 9000
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
