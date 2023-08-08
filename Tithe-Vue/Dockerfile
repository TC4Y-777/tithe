FROM node:16-alpine

WORKDIR /vue

COPY package.*.json ./

RUN npm install

COPY . .

RUN npm run build
