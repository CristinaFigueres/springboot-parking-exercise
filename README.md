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

### Funcionality improvements

1.- Se parte de un ejemplo: initial commit with spring-boot-2-rest-service-with-swagger
2.- Consideramos que solo las plazas que comparten un lado con la pedestrian exit tienen salida pedestrian
3.- El index empieza en 1 de las plazas de parking
4.- http://localhost:8080/swagger-ui.html para la ui del swagger
5.- Guardar la posicion del bay no como  pk sino como un campo aparte
quitar el \n del final del toString
el TODO del Repository

### Technical improvements

Use of 
