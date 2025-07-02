FROM openjdk:22
COPY target/customer-logistics-api-0.0.1-SNAPSHOT.jar customer-logistics-api-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/customer-logistics-api-0.0.1-SNAPSHOT.jar"]