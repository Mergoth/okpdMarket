FROM java:8-jre
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

ENV CONFIG_SERVICE_PASSWORD 123

COPY ./build/registry.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/registry.jar"]

EXPOSE 8761
