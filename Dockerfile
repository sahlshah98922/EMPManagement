FROM eclipse-temurin:8-jre

WORKDIR /app

COPY target/Employee_Management_System-0.0.1-SNAPSHOT.war app.war

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.war"]
