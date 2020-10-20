# api-user-management

##Overview

This Spring Boot Java 11 application exposes 2 REST endpoints.

1. Get User 

    Get /api/user/management/v1/users/{Id}


2. Update/Patch User

    Patch /api/user/management/v1/users/{Id}
    
##Run Instructions

Execute the following maven command or import the project into your favourite IDE and use the embedded maven to start the application.

    mvn clean spring-boot:run
    
Application will run on port 8090.


##Get User API

Sample curl command to hit Get User endpoint.

    curl --request GET 'http://localhost:8090/api/user/management/v1/users/10001' --header 'Authorization: Basic bWVtYmVyOnBhc3N3b3Jk'
    
    
##Update/Patch User API

Sample curl command to hit Patch User endpoint.

    curl --request PATCH 'http://localhost:8090/api/user/management/v1/users/10002' --header 'Authorization: Basic YWRtaW46cGFzc3dvcmQ=' --header 'Content-Type: application/json' --data-raw '{"gender": "Male", "address": {"city": "Parramatta"}}'

"Update" functionality is implemented following "JSON Merge Patch" guidelines.

Here is the link to its RFC.

https://tools.ietf.org/html/rfc7396

Note: On successful update of the user record in database, the API will return the updated record.


##Initial data

Following five records are loaded into in-memory H2 database during startup.

ID  	TITLE  	FIRSTNAME  	LASTNAME  	GENDER  	STREET  	CITY  	STATE  	POSTCODE  

10001	Ms	Dion	Frost	Female	40 Little Myers Street	LETHBRIDGE	Victoria	3332

10002	Ms	Zelma	Fry	    Male	29 Ridge Road	Parramatta	Queensland	4660

10003	Mr	Thad	Ewing	Male	38 Kopkes Road	PITFIELD	Victoria	3351

10004	Mr	Lloyd	Walls	Male	48 Sunnyside Road	SPECTACLE LAKE	South Australia	5345

10005	Dr	Keven	Wade	Male	41 Ridge Road	TAUNTON	Queensland	4674


##Security

The APIs are protected by in-memory Basic Authentication and Role based Authorization.

Following two users are loaded into memory during startup.

User 1

Username: member
Password: password
Role: MEMBER  

User 2

Username: admin
Password: password
Role: ADMIN

MEMBER users are authorized for Get User endpoint alone. ADMIN users are authorized for both the endpoints.


##Data Layer

Spring Data JPA is used to manage database interactions. Database transactions will timeout after 5 seconds.


##Testing

Tests are added for web layer, service layer and end-to-end integration.

For now, I have added Pact tests for Get User API alone.

Please execute "mvn test" command to run all the tests.


##Logging

API request and response logging is added with the help of logback-access module.

Since this is a demo application, the entire request is being logged along with Basic Auth credentials.


####Thank you. Looking forward to discuss with you.
