FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 10002
COPY target/test-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=10002","-jar","/app.jar"]