FROM openjdk:17
COPY target/library-app.jar /app.jar
CMD ["java", "-jar", "/app.jar"]
