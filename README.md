# To run for user:

## Start server

In a bash terminal:

1. cd server
2. mvn package
3. ./run

## Start client

In a terminal:

1. cd client
2. npm install
3. npm start

## Go to http:/localhost:8000 in a browser window

# To run tests:

## Run backend tests

In a bash terminal:

1. cd server
2. mvn package OR mvn test

## Run frontend test

(Start server) In a terminal:

1. cd server
2. mvn package
3. ./run

In a terminal:

1. cd client
2. npm install
3. npx playwright install (!!!)
4. npm test
