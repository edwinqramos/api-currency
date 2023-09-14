docker run -d -p 27017:27017 --name test-mongo mongo:latest

docker build -t api-currency:latest .

docker run --name api-currency -p 8080:8080 api-currency