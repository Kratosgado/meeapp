FROM azul/zulu-openjdk:21-latest AS build

WORKDIR /workspace/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src  src

RUN ./gradlew build -x test

FROM azul/zulu-openjdk:21-latest
VOLUME [ "/tmp" ]
COPY --from=build /workspace/app/build/libs/*.jar app.jar
EXPOSE 9000
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
