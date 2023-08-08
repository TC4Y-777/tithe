FROM node:16-bookworm-slim

RUN apt-get update -y && apt-get upgrade -y
RUN apt-get install systemctl -y
RUN apt-get install nginx -y

WORKDIR /vue

COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build
RUN rm -r /var/www/html/* && cp -r dist/* /var/www/html/

EXPOSE 80

ENTRYPOINT ["systemctl", "start", "nginx"]
