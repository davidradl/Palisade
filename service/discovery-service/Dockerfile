FROM openjdk:11
VOLUME /tmp

ARG JAR_FILE

ADD $JAR_FILE /usr/share/discovery-service/discovery-service.jar

ENTRYPOINT ["java", "-jar", "/usr/share/discovery-service/discovery-service.jar"]