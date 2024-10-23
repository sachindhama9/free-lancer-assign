# Dockerfile
FROM openjdk:8-jdk-slim
VOLUME /tmp
COPY target/freelancer-api.jar freelancer-api.jar
ENTRYPOINT ["java", "-jar", "freelancer-api.jar"]
