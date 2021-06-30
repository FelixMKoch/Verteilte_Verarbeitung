FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY ./build/libs/*.jar /opt/app
# ENV PORT=1024
# ENV JSON_FILE = "json-file.log"
# ENV LOG_FILE = "information.log"
# ENV LOG_LEVEL = "INFO"
EXPOSE 1024
CMD ["java", "-jar", "/opt/app/vv-studienarbeit-1.0-SNAPSHOT.jar"]
