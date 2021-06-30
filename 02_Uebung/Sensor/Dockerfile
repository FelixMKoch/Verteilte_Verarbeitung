FROM inf-docker.fh-rosenheim.de/studwinten4338/docker-images/gradle:6.8.3-jdk11
RUN mkdir /opt/app
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} /opt/app/application.jar
ENTRYPOINT ["java", "-jar", "/opt/app/application.jar"]
