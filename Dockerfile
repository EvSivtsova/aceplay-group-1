FROM openjdk:17-jdk-slim-buster

EXPOSE 8080

RUN mkdir /app

COPY build/libs/*.jar /app/aceplay_spring_app.jar

ENTRYPOINT ["java", "-jar", "/app/aceplay_spring_app.jar"]