FROM java:8-jre
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

ADD ./build/config.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/config.jar"]

EXPOSE 8888