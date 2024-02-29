# create jar
FROM gradle:8.5.0-jdk17 AS build

# install dependencies and cache them
WORKDIR /app
COPY ./build.gradle /app/
COPY ./settings.gradle /app/
RUN gradle dependencies

# build jar
COPY /src /app/src
RUN gradle bootJar

# run that jar
FROM openjdk:17-oracle
COPY --from=build /app/lib/ChatService.jar /app/ChatService.jar
EXPOSE 8124
CMD ["java","-jar","-Dspring.profiles.active=dev", "/app/ChatService.jar"]
