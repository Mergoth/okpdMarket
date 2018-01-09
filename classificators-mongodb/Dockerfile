FROM mongo:3.6.0-jessie
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

#ARG MONGODB_PASSWORD
ENV MONGODB_PASSWORD 123
ENV INIT_DUMP_DIR /dump
COPY init.sh /init.sh
COPY ./dump /dump

RUN \
 chmod +x /init.sh && \
 apt-get update && apt-get dist-upgrade -y && \
 apt-get install psmisc -y -q && \
 apt-get autoremove -y && apt-get clean && \
 rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["/bin/sh"]