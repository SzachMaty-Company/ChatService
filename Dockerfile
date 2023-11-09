FROM openjdk:17-oracle
COPY build/libs/ChatService-0.0.1-SNAPSHOT.jar /app/chatService.jar
EXPOSE 8080
CMD ["java","-jar","/app/chatService.jar"]
