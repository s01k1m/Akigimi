FROM nginx:1.22-alpine

RUN apk add --no-cache logrotate

RUN mkdir -p /usr/share/nginx/application/access
RUN mkdir -p /usr/share/nginx/application/error

COPY ./conf/logrotate.conf /etc/logrotate.conf
COPY ./nginx /etc/logrotate.d/nginx
COPY ./script.sh /script.sh
RUN dos2unix /etc/logrotate.d/nginx
RUN chmod +x /script.sh

ENTRYPOINT ["/script.sh"]