# Rate Limiter App
Simple implementation of Rate Limiter to monitor the number of request using Java Spring Boot.

# Requirements
For building and running the application you need:
JDK 8
Maven 3
IDE Eclipse/intellIJ IDE/Spring tool suite

# Steps to run
Build the project using `mvn clean install`
Run using `mvn spring-boot:run`
The web application is accessible via localhost:8080

# About the Service
The service is a simple application where the incoming request limit can be observe over desired time limit.
It uses H2 in-memory database to store data. 
Can also use relational database like MYSQL with proper configuration.
You can call the REST endpoint '/getNames' with user id as query param,as mentioned in the controller on ***port 8080***.

Desired Time limit - 09:00:00 am to 05:00:00pm
Desired request limit for each individual - 5 counts
Desired Api Rate count - 20 counts

URL :`http://localhost:8080/getNames?id=101`

# Running the project with H2

In pom.xml add:
        <dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
		</dependency>
  
#### Append this to the end of application.properties file:
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=root
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect


#### To view your H2 in-memory database
To view and query the database you can browse to http://localhost:8080/h2-console. 
Default username is 'sa' with 'root' as password and along with database url as 'jdbc:h2:mem:testdb'



