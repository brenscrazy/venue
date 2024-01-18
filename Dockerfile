FROM openjdk:21
VOLUME /tmp
EXPOSE 8080
COPY target/venue-0.0.1-SNAPSHOT.jar venue.jar
ENTRYPOINT ["java","-jar","/venue.jar"]