FROM openjdk:17-jdk-alpine
EXPOSE 8080
ENTRYPOINT ["java","-jar", "/usr/local/lib/app.jar"]