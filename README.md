# Parking Exercise

Exercise performed using as a basis the example that can be found in `https://github.com/in28minutes/spring-boot-examples/tree/master/spring-boot-2-rest-service-with-swagger`

## Build

Application uses Maven as build system, so in order to generate the executable JAR file you only need to execute:

```bash
$ mvn package
```

The executable file can be found at `[...]/target/parking-1.0.0.jar`.


## Run

You can run the server with the following command:

```bash
$ java -jar parking-1.0.0.jar
```

Server will start listening on port `8080`. Example [http://localhost:8080]().

## Endpoints documentation

Description of available endpoints, UI Swagger REST API via:

* `swagger-ui.html`: [http://localhost:8080/swagger-ui.html] for the REST specification.

### Available users

There are two user configurated in the Basic Auth:

* Username: `admin`
* Password: `admin`
* Roles: `ADMIN`

and 

* Username: `user`
* Password: `password`
* Roles: `USER`


## Tests

The repo contains unit test files (Unitary and Integration test). 

In order to run Maven integrated tests, run the following command:

```bash
$ mvn clean verify
```

## Improvements

Improvements that can be implemented in order to obtain a better solution (//TODO:)

### Funcionality improvements

* The position of each bay inside the parking lot starts with 1 and in the enunciate seems that is defined as 0
* It would be easier if the bay position is stored in a new fild instead of use the Priary key for this matter



### Technical improvements

* Use some tool to generate tests reports like `jacoco`
* Achieve with jpa return only the first value in the firstAvailable list o ParkingBay
* Use of JavaDoc
* Catch of exceptions
* Input validations
* Use of interfaces


