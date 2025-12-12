FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/cassangolo_app.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
