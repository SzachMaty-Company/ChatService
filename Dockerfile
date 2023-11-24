FROM openjdk:17-oracle
COPY lib/ChatService.jar /app/chatService.jar
EXPOSE 8124
CMD ["java","-jar","-Dspring.profiles.active=dev", "/app/chatService.jar"]