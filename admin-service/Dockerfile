# Start with a base image containing Java runtime
FROM openjdk:8-jdk-alpine

# Add Maintainer Info
MAINTAINER DuyDQ2 <duydq2@fpt.com.vn>

# Add a volume pointing to /tmp
VOLUME /tmp

# Make port 8092 available to the world outside this container
EXPOSE 8092

# The application's jar file
ARG JAR_FILE=target/system-service-0.0.1-SNAPSHOT.jar

# Add the application's jar to the container
ADD ${JAR_FILE} system-service.jar

# Run the jar file 
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/system-service.jar"]

