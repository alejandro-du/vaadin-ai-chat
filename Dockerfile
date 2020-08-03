# Build stage
FROM maven:3-jdk-11 as build
RUN curl -sL https://deb.nodesource.com/setup_12.x | bash -
RUN apt-get update -qq && apt-get install -qq --no-install-recommends nodejs
WORKDIR /usr/src/app/
COPY src src
COPY frontend frontend
COPY pom.xml .
RUN mvn clean package -DskipTests -Pproduction

# Note: The Run stage is not connected to the Build stage, other than via the target jar that is copied into it.

# Run stage
FROM openjdk:11
COPY --from=build /usr/src/app/target/*.war /usr/app/app.war
RUN useradd -m myuser
USER myuser
EXPOSE 8080
CMD java -jar /usr/app/app.war
