# customer-logistic-api

Microservicio securizado vía tokens que permite almacenar informacion logistica de clientes authorizados

type: `microservice`
domain:  `logistic-data`
group:  `customers`
owner:  `Nauta`

# Tabla de Contenidos

1. [Documentación](#Documentación)
2. [Integración con éste microservicio](#Integración-con-éste-microservicio)
3. [Proyecto](#Proyecto)
4. [Stack](#Stack)
5. [Setup Local](#Setup Local)
6. [Build and UP](#Build and UP)
7. [Dependencias del sistema](#Dependencias del sistema)

## Documentación

* Documentación Open Api: `v3/api-docs`
* [Swagger Hub](https://app/v3/api-docs)

## Proyecto

* [SpringBoot Docs](https://spring.io/projects/spring-boot)
* [SpringSecurity Docs](https://docs.spring.io/spring-security/reference/index.html)

* Sugiere la utilización de Junit y Mockk para tests unitarios

## Stack

- _Lenguaje:_ `Kotlin <1.9.25>`
- _Lenguaje:_ `Java <21>`
- _Framework:_ `Springboot <3.5.3.RELEASE>`
- _Security:_ `SpringSecurity <6.5.1.RELEASE>`
- _Database:_ `Postgres <42.7.3>`
- _Database:_ `Flyway <11.7.2>`

## Setup Local

_Variables de Entorno_

```bash
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/customer-logistics-db-dev;SPRING_DATASOURCE_USERNAME=svcCustomerLogisticsDev;SPRING_DATASOURCE_PASSWORD=userDev2025```
```

## Build and UP

El proyecto cuenta con la posibilidad de ser levantado en un entorno basado en contenedores mediante docker.

Como requisito previo se requiere la instalación de [docker](https://docs.docker.com/install/).

* [Documentación Docker](https://docs.docker.com/)

Levantar el proyecto local con docker

```bash
  mvn clean install
  docker build --build-arg JAR_FILE=target/*.jar -t nauta/customer-logistics-api .
  docker run -p 8080:8080 nauta/customer-logistics-api
```

Levantar el proyecto local con docker compose

```bash
  mvn clean install
  docker build -t customer-logistics-api .
  docker-compose up
```

## Dependencias del sistema

_Se requiere la coneccion a una base de datos postgres para poder levantar y
se utiliza flyway para crear la estructura_

- `Postgres SQL`

```bash
  create database :
        1 : docker pull postgres
        2 : docker run --name postgres \
            -e POSTGRES_DB=customer-logistics-db-dev\
            -e POSTGRES_USER=svcCustomerLogisticsDev \
            -e POSTGRES_PASSWORD=userDev2025 \
            -p 5432:5432 \
            -d postgres
            
          3 : iniciar o detener la img de postgres
            sudo docker start <container-name || container-id>
            sudo docker stop <container-name || container-id>
```