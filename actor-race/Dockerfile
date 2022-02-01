#
# Build stage
#
FROM maven:3.8.3-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:17-alpine
COPY --from=build /home/app/target/actorrace-team-java-10-1.0.jar /usr/local/lib/actorrace-team-java-10.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/actorrace-team-java-10.jar"]
