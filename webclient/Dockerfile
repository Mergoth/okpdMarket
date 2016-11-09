# To build and run with Docker:
#  $ docker build -t weblicent .
#  $ docker run -d -p 8080:4200 -it weblicent
#
FROM node:6

RUN useradd --user-group --create-home --shell /bin/false app

ENV APP_NAME "webclient"
ENV APP_USER "app"
ENV HOME /home/$APP_USER
ENV APP_DIR $HOME/$APP_NAME

RUN npm install --global angular-cli

WORKDIR $APP_DIR
COPY package.json $APP_DIR/package.json
RUN npm install && npm cache clean
COPY . $APP_DIR
RUN chown -R $APP_USER:$APP_USER $HOME/*

USER $APP_USER
WORKDIR $APP_DIR

EXPOSE 4200

CMD ["ng", "serve", "--prod"]