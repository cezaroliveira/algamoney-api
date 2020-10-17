FROM openjdk:8-jdk-alpine

# Create a user with non-root privileges
RUN addgroup -S spring && adduser -S algamoneyapi -G spring

# Patterns => user:group
USER algamoneyapi:spring

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} algamoneyapi.jar

ENTRYPOINT ["java","-jar","/algamoneyapi.jar"]
