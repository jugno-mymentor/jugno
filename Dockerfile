FROM openjdk:17-alpine
#TODO-CICD: Should we use unbuntu instead of alpine? bc on github our host is ubuntu?
MAINTAINER Muhammad Jawad Butt <m_jawad_butt@yahoo.com>
ADD $JAR_FILE_DIR/$JAR_FILE_NAME $JAR_FILE_NAME
EXPOSE 8080
ENTRYPOINT ["java", "-jar","$JAR_FILE_NAME"]

# Example of multistage example that builds also on the docker container rather than on host. The second stage
# only includes the runtime.
#FROM maven:3.5.4-jdk-8-alpine as maven
#COPY ./pom.xml ./pom.xml
#COPY ./src ./src
#RUN mvn dependency:go-offline -B
#RUN mvn package
#FROM openjdk:8u171-jre-alpine
#WORKDIR /adevguide
#COPY --from=maven target/SimpleJavaProject-*.jar ./SimpleJavaProject.jar
#CMD ["java", "-jar", "./SimpleJavaProject.jar"]
