# create jar
FROM gradle:8.5.0-jdk17 AS build
COPY . /app
WORKDIR /app
RUN gradle bootJar 

# run that jar
FROM openjdk:17-oracle
COPY --from=build /app/lib/ChatService.jar /app/ChatService.jar
EXPOSE 8124
CMD ["java","-jar","-Dspring.profiles.active=dev", "/app/ChatService.jar"]
