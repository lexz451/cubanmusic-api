#!/bin/bash
mvn clean package -DskipTests=true
nohup mvn spring-boot:run > ./log.txt 2>&1 &
echo $! > ./pid.file