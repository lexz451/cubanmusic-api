FROM openjdk:16-alpine
WORKDIR ./
EXPOSE 8080
CMD ['java', '-jar', './target/cubanmusic-api-0.0.1-SNAPSHOT.jar', '-Denv=.env']