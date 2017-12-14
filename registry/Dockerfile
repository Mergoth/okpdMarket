FROM java:8-jre
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

ENV CONFIG_SERVICE_PASSWORD 123

ADD ./build/registry.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/registry.jar"]

EXPOSE 8761
