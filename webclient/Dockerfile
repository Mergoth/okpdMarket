# To build and run with Docker:
#  $ ng build --prod
#  $ docker build -t weblicent .
#  $ docker run -p 80:80 -it weblicent
#
FROM nginx
COPY dist /usr/share/nginx/htm
EXPOSE 80