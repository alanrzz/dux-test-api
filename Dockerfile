FROM amazoncorretto:17
COPY target/futbol.api-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]