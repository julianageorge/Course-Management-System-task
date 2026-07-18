FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
ENV MAVEN_OPTS="-Djdk.tls.client.protocols=TLSv1.2"
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests -Dmaven.wagon.http.retryHandler.count=5 package

FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
