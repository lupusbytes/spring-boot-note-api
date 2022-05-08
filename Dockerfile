FROM openjdk:17-jdk-alpine

ARG MONGODB_CONNSTRING
ENV MONGODB_CONNSTRING={MONGODB_CONNSTRING}

# Run application under a dedicated user, instead of root
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080/tcp
ENTRYPOINT ["java","-jar","/app.jar"]
