# run this
mvn clean spring-boot:run


# curl the following commands

curl -v localhost:8090/api/cars
curl -v localhost:8090/api/cars/166


[//]: # (curl -X POST localhost:8090/cars -H 'Content-type:application/json' -d '{"make": "Honda", "model": "Civic", "description":"Honda Civic description"}')