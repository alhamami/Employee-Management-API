FROM openjdk:23-jdk-slim

WORKDIR /app

COPY target/employee-management-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

CMD ["java", "-jar", "app.jar"]