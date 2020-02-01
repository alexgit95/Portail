FROM maven:3.6.1-jdk-11-slim AS builder
COPY . /src
WORKDIR /src
RUN mvn package -DskipTests
RUN ls /src/target

FROM adoptopenjdk/openjdk11:x86_64-debian-jre-11.0.3_7
COPY --from=builder /src/target /apps
ENV AWS_CREDENTIAL_PROFILES_FILE=/credentials/credentials.aws
WORKDIR /apps
EXPOSE 7777
ENTRYPOINT ["java", "-Xmx80m","-jar", "portail-0.0.1-SNAPSHOT.jar"]




