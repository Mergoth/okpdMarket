FROM java:8-jre
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

ENV MONGODB_PASSWORD 123
ENV CONFIG_SERVICE_PASSWORD 123

COPY ./build/classificators.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/classificators.jar"]

EXPOSE 8080