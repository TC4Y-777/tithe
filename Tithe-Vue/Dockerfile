FROM node:16-bookworm-slim

RUN apt-get update -y && apt-get upgrade -y
RUN apt-get install systemctl -y
RUN apt-get install nginx -y

WORKDIR /vue

COPY package*.json ./
RUN npm install

COPY . .

EXPOSE 5173

ENTRYPOINT ["npm", "run", "dev"]
