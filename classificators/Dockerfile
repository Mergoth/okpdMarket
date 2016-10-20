FROM java:8-jre
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

ADD ./build/classificators-service.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/account-service.jar"]

EXPOSE 6000