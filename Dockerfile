
#FROM openjdk:25-jdk-slim-bullseye AS builder


#echo "deb http://mirrors.aliyun.com/debian/ bullseye main contrib non-free" > /etc/apt/sources.list && \
#    echo "deb http://mirrors.aliyun.com/debian-security/ bullseye-security main" >> /etc/apt/sources.list && \
#    echo "deb http://mirrors.aliyun.com/debian/ bullseye-updates main contrib non-free" >> /etc/apt/sources.list && \
# RUN apt update && apt install -y maven

#COPY pom.xml .
#RUN mvn dependency:go-offline
#COPY src ./src
#RUN mvn package -DskipTests
FROM openjdk:25-jdk-slim-bullseye

WORKDIR /app

COPY target/platform-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java",  "-Duser.timezone=Asia/Shanghai","-jar", "app.jar"]