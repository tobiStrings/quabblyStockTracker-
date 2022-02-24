#Stock Tracker 


### Project description
#### This is a simple stock tracking logistics system with CRUD functionality The project was built with SpringBoot(Java).
#Prerequisites

* Java 16
* Maven
* PostMan
* MongoDb

##To build shell mvn clean install

##To run shell java -jar ./target/stockTracker.jar

#Rest API Usage *

##Create Stock Item

POST  http://localhost:8080/api/v1/stocks
#####Parameter json {
    "stockName":"String",
    "stockPrice":double
}
### Response 201 CREATED on successful request
####json {
"id": "String",
"stockName": "String",
"currentPrice": double,
"currentDate": "time stamp",
"updatedAt": "time stamp"
}

#Get stock by the stock id

GET http://localhost:8080/api/v1/stocks/{id}
##Response 302 FOUND on successful request
####json {
    "id": "String",
    "stockName": "String",
    "currentPrice": double,
    "currentDate": "time stamp",
    "updatedAt": ""time stamp
}

#Get all stocks 

GET http://localhost:8080/api/v1/stocks
#### Returns list of stocks
## Response 302 FOUND

#Update Stock
PUT http://localhost:8080/api/v1/stocks/{id}

##Response 200 Ok on successful request
####json {
    "id": "String",
    "stockName": "String",
    "currentPrice": double,
    "currentDate": "time stamp",
    "updatedAt": ""time stamp
}


#Delete stock by id 
DELETE http://localhost:8080/api/v1/stocks/{id}
##Response 200 OK on a successful request
#### Stock successfully deleted 