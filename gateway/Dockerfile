FROM java:8-jre
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

ADD ./build/gateway.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/gateway.jar"]

EXPOSE 4000

HEALTHCHECK CMD "curl -f http://localhost:4000/health"