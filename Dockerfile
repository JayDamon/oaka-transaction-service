FROM openjdk:16-jdk-buster
COPY ./target/*.jar /usr/local/lib/app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/usr/local/lib/app.jar"]