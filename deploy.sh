docker-compose down --rmi all
mvn clean package -DskipTests=true
docker-compose up -d