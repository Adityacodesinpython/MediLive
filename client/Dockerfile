FROM node:22.8.0-alpine

WORKDIR /app

COPY package*.json ./
#COPY components ./

RUN npm install

COPY . .

#RUN npm run build

EXPOSE 4200

CMD ["npm", "start"]
