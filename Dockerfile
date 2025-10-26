FROM eclipse-temurin:24.0.2_12-jre

EXPOSE 8080

COPY build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]
