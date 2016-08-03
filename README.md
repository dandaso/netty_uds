# README
 netty echo server with unix domain socket
 
## Build
 ./gradlew clean fatJar

## Run server
 java -cp build/libs/netty_uds.jar UdsEchoServer

## Run client
 ruby tool/client.rb

