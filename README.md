### Usage
#### Docker installation
If you don't have docker installed on your system:
- On Linux:
`sudo apt install -y docker`
- On Windows:
`winget install Docker.DockerDesktop`

#### Run Docker Image
```bash
# in Infrastruktura directory
docker compose --file backend-docker-compose.yml up --build chatservice chatservice-db
```

#### Stop Docker Image
```bash
# in Infrastruktura directory
docker compose --file backend-docker-compose.yml down
```

#### API
- for authentication in stomp, add `token` header with jwt in `CONNECT` frame
##### WebSockets
- _ws://localhost:8124/registerws_ - connect to websocket
- (stomp) `SUBSCRIBE` _/user/queue/message_ - for receiving messages
payload structure
```json
{
    "chatId": "<number>",
    "senderId": "<string>",
    "timestamp": "<string>",
    "message": "<string>"
}
```
- (stomp) `SEND` _/chat/message_ - for sending messages
```json
{
    "chatId": "<number>",
    "message": "<string>"
}
```

##### Http 
- _http://localhost:8124/swagger-ui/index.html_ - swagger

#### Tips
test websocket client can be found in files: /src/main/resources/static/client.html  
REMEMBER!!! Make sure that you haven't changed HOST port(8124), because this static client uses this port for connection with websockets(ofc you can change it manually in code).  

