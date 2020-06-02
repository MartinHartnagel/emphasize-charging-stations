# Emphasize IT general coding example

This is an example implementation by Martin Hennig (Emphasize IT) of the task "develop a REST Interface for storage and retrieval of charging station data" (see task-description.txt) given.

## REST service

Requests _do not_ require any authentication header.

To add a new charging station execute:

`curl -X POST "http://localhost:8080/charging-stations/v1.0/charging-stations" -H  "accept: */*" -H  "Content-Type: application/json" -d "{  \"id\": \"12345678-5555-4444-4444-1234567890ab\",  \"latitude\": 0.123,  \"longitude\": 7.234,  \"postalCode\": \"12347\"}"`

To retrieve the added charging station again:

`GET http://localhost:8080/charging-stations/v1.0/charging-stations/12345678-5555-4444-4444-1234567890ab`

This returns the charging station with the id 12345678-5555-4444-4444-1234567890a.

To retrieve all charging stations with postal code 12345:

`GET localhost:8080/charging-stations/v1.0/charging-stations/by-postal-code/12345`

returns the charging stations with a postal code equal to 12345.

### Swagger description

When running locally:

* Swagger file:	http://localhost:8080/charging-stations/v2/api-docs
* Swagger ui:		http://localhost:8080/charging-stations/swagger-ui.html

## Development

To build, test and run this project please install Apache Maven and Docker Cli.

Then execute from a shell:

`mvn clean install` 

To start spring-boot locally run:

`mvn spring-boot:run`

To run the service with docker run:

`docker run --rm -p 8081:8081 -p 8080:8080 emphasize-charging-stations:latest`

## Test cases

The following is a draft of test cases which may be performed on the running instance.
All of them can be handled by the use of the Swagger UI provided in the running instance.

1. Open the Swagger UI and add several charging stations
    1. *add a new charging station* with the body: <pre>{
  "id": "12345678-5555-4444-4444-1234567890ab",
  "latitude": 0.123,
  "longitude": 7.234,
  "postalCode": "12347"
}</pre>
*Expected Response* Code: 200
    1. *try adding a charging station with the same id* again with the body: <pre>{
  "id": "12345678-5555-4444-4444-1234567890ab",
  "latitude": 13.166,
  "longitude": 8.234,
  "postalCode": "24354"
}</pre>
*Expected Response* Code: 406, message: "Charging Station with ID '12345678-5555-4444-4444-1234567890ab' already exists"
    1. *add another charging station* with the body: <pre>{
  "id": "23345678-5555-4444-4444-1234567890ab",
  "latitude": 13.123,
  "longitude": 52.234,
  "postalCode": "12347"
}</pre>
*Expected Response* Code: 200
1. Query for existing and non existing charging stations by id
    1. Verify two successfully added charging stations exist by querying *get a charging station* with id:
        * 12345678-5555-4444-4444-1234567890ab *Expected Response* Code: 200, body: <pre>{
  "id": "12345678-5555-4444-4444-1234567890ab",
  "latitude": 0.123,
  "longitude": 7.234,
  "postalCode": "12347"
}</pre>
        * 23345678-5555-4444-4444-1234567890ab *Expected Response* Code: 200, body: <pre>{
  "id": "23345678-5555-4444-4444-1234567890ab",
  "latitude": 13.123,
  "longitude": 52.234,
  "postalCode": "12347"
}</pre>
1. Query for existing charging stations by postal code
    1. Verify two successfully added charging stations exist by querying *get all charging stations with a postal code* with postal code:
        * 12347 *Expected Response* Code: 200, body (the order may vary): <pre>[
  {
    "id": "12345678-5555-4444-4444-1234567890ab",
    "latitude": 0.123,
    "longitude": 7.234,
    "postalCode": "12347"
  }, {
    "id": "23345678-5555-4444-4444-1234567890ab",
    "latitude": 13.123,
    "longitude": 52.234,
    "postalCode": "12347"
  }
]</pre>
        * 12340 *Expected Response* Code: 200, body: <pre>[]</pre>
    
