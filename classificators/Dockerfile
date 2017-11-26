FROM java:8-jre
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

ADD ./build/classificators.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/classificators.jar"]

EXPOSE 8080