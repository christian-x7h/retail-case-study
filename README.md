# Sample Retail Application

(Part of a case-study series)

This application is built using Spring Reactive Web and Azure Cosmos.

## Configuration

Add an `application-prod.properties` to resources folder and override
sample values from `dev` with actual credentials for an Azure Cosmos
datastore.

## Run tests

From project root, run:

`./gradlew test`

## Run the application locally

From project root, run:

`SPRING_PROFILES_ACTIVE=prod ./gradlew bootRun`

Then open the application in a browser at `http://localhost:8080/actuator/health`. 

You should see a JSON status response of "UP".

## Sample Curl Commands

Update price for id:
```
curl -v -XPUT -H "Content-type: application/json" -d '{ "price": 22.25, "currencyCode": "USD" }' 'http://localhost:8080/v1/products/13860428/price'
```

Get product data for id:
```
curl -v 'http://localhost:8080/v1/products/13860428'
```

## TODOs
* Test suite
* Add currency to API get
* Auto-convert other currency to requested currency
* Validation for Enums
