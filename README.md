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

### Usage For Local Developement
TODO

#### Test if app is running:
`curl http://localhost:8124/test`
#### API
- /test -> to test connection
- /registerws -> to register websocket connection
- /topic/chat -> to subscribe to channel(for receiving messages)
- /app/chat -> for sending messages to the channel

#### Tips
I also implemeneted small testing client which is static HTML page. if you want to use it you need to move to the /src/main/resources/static/client.html  
REMEMBER!!! Make sure that you haven't changed HOST port(8124), because this static client uses this port for connection with websockets(ofc you can change it manually in code).  

