FROM openjdk:8-jre-alpine
VOLUME /tmp
COPY maven/*.jar /app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
EXPOSE 8888