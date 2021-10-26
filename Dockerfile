FROM openjdk:11-jdk-buster

# cd /opt/app
WORKDIR /opt/app

# Refer to Maven build -> finalName
ARG JAR_FILE=target/*.jar

# cp target/backend-technical-test-2.0.0-SNAPSHOT.jar /opt/app/app.jar
COPY ${JAR_FILE} app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]