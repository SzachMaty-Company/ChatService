#build stage
FROM gradle:8.4.0-jdk17-alpine AS BUILD_STAGE

WORKDIR /app
COPY . /app
RUN gradle build

#package stage
FROM openjdk:17-oracle

WORKDIR /app
COPY --from=BUILD_STAGE /app/build/libs/ChatService-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT exec java -jar app.jar