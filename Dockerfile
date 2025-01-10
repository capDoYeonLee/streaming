FROM --platform=linux/amd64 openjdk:17-oracle
ARG JAR_FILE="./build/libs/*.jar"
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]