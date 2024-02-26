### Usage
#### Docker installation
If you don't have docker installed on your system:
- On Linux:
`sudo apt install -y docker`
- On Windows:
`winget install Docker.DockerDesktop`

### Docker Image Creation For Dev
`docker build -t docker build -t`

#### Run Docker Image
`docker compose --file docker-compose.dev.yml up`

#### Stop Docker Image
`docker compose --file docker-compose.dev.yml down`

#### API
- for authentication in stomp, add `token` header with jwt in ==CONNECT== frame
- http requests will be authenticated by auth gateway
##### WebSockets
- _ws://localhost:8124/registerws_ - connect to websocket
- (stomp) _==SUBSCRIBE==_ _/user/queue/message_ - for receiving messages
payload structure
```json
{
    chatId: <number>,
    senderId: <string>,
    timestamp: <string>,
    message: <string>
}
```
- (stomp) _==SEND==_ _/chat/message_ - for sending messages
```json
{
    chatId: <number>,
    message: <string>
}
```

##### Http
- _http://localhost:8124/user/{userId}/chats_ - user chats (will change later)
- _http://localhost:8124/chat/{chatId}/messages_ - chat messages

#### Tips
test websocket client can be found in files: /src/main/resources/static/client.html  
REMEMBER!!! Make sure that you haven't changed HOST port(8124), because this static client uses this port for connection with websockets(ofc you can change it manually in code).  

