FROM openjdk:16-alpine
COPY ./target/ /usr/src/cubanmusic
WORKDIR /usr/src/cubanmusic
EXPOSE 8080
CMD ['java', '-jar', 'cubanmusic-api-0.0.1-SNAPSHOT.jar']