FROM maven:3.8.3-openjdk-17 AS MAVEN_BUILD

MAINTAINER Edwin Quispe

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:17-oracle

WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/currency-0.0.1.jar /app/

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "currency-0.0.1.jar"]