
FROM openjdk:17-jdk-slim


WORKDIR /app


COPY target/testcase-0.0.1-SNAPSHOT.jar demo.jar


EXPOSE 8080

CMD ["java", "-jar", "demo.jar"]