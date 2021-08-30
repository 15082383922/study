FROM adoptopenjdk/openjdk11:jre-11.0.11_9-alpine

EXPOSE 10002
COPY target/test-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=10002","-jar","/app.jar"]