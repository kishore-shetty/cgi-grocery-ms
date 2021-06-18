# Grocery API

This API provides various capabilities

## API Functionalities
* Generate a list of vegetable/fruit sorted by name, their maximum price ,date on which price was max
* Generate a report for each Item Name, showing how their price trended
* RestAPI takes the string and return the matching items

## App Startup Functionalities
* StartupEvent is startup event, that runs when application starts.
* This event will read the data from excel and saves it to mongoDB

## Installation

Build & test application
```bash
mvn clean install
```

Running the Application
```bash
mvn spring-boot:run
```

## Swagger Link
* Application should be started to view swagger link
```bash
http://localhost:8081/swagger-ui/#/
```

## DB Usage
* This application uses embedded mongo DB

```properties
host=localhost
port=27017
database=grocery-db
```

## Endpoint Usage
* Endpoint returns list of groceries sorted by name, price & date
* pageNo & pageSize is non-mandatory. Defaults to 0 & 20 respectively.
```yaml
GET: http://localhost:8081/groceries?pageNo=0&pageSize=20
``` 

* Endpoint returns list of groceries with price and date for particular item.
* pageNo & pageSize is non-mandatory. Defaults to 0 & 20 respectively.
```yaml
PUT: http://localhost:8081/groceries/search?name=Amla&pageNo=0&pageSize=20
```

* Endpoint returns report for each Item Name, showing how their price trended
* name is non-mandatory.
```yaml
GET: http://localhost:8081/groceries/report?name=Amla
```

## Reference
* https://bitbucket.org/sathishcgi/groceryassignment/src/master/