docker-compose down --rmi all
mvn clean package
docker-compose up -d
