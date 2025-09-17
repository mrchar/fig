FROM node:22 AS builder
RUN mkdir /opt/fig
WORKDIR /opt/fig
COPY . .
RUN corepack enable && yarn install && yarn build

FROM nginx:1.29
COPY --from=builder /opt/fig/dist /usr/share/nginx/html
COPY ./nginx.conf /etc/nginx/conf.d/default.conf
