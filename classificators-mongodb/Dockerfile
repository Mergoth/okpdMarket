FROM mongo:3
MAINTAINER Vladislav Vorobev <nowspamhere@gmail.com>

#ARG MONGODB_PASSWORD
ENV MONGODB_PASSWORD 123
ENV INIT_DUMP_DIR /dump
ADD init.sh /init.sh
ADD ./dump /dump

RUN \
 chmod +x /init.sh && \
 apt-get update && apt-get dist-upgrade -y && \
 apt-get install psmisc -y -q && \
 apt-get autoremove -y && apt-get clean && \
 rm -rf /var/cache/* && rm -rf /var/lib/apt/lists/*

ENTRYPOINT ["/bin/sh"]