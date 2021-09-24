#!/bin/bash
mvn clean package -DskipTests=true
nohup java -jar ./target/cubanmusic-api-0.0.1-SNAPSHOT.jar > ./log.txt 2>&1 &
echo $! > ./pid.file