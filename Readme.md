docker run -d -p 27017:27017 --name test-mongo mongo:latest

docker build -t api-currency:latest .

docker run -e DB_URI=mongodb://mongoadmin:P4ssw0rd.@192.168.186.1:27017/currency?authSource=admin -e DECIMALES=3 -e APP_SECURITY_USER=usrserv_apirest -e APP_SECURITY_PASSWORD=p@ssw04rd --name api-currency -p 8080:8080 api-currency

