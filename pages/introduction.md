# Introduction

## Used Technologies
- Spring Boot
- Spring WebClient
- SnakeYaml

## Repository Structure
```
microservice_management/                     
└── src/
    └──main/java/edu.kit.microservice_management/                       
        ├── communication/        # contains web servers
        ├── configuration/
        ├── controller/
        ├── entity/       # contains gitlab entities
        ├── external/       # contains Prometheus and Domain Service
        ├── helm/       # contains helm objects
        ├── json/    
        ├── links/
        ├── logging/    
        ├── microservice/       # microservice factory
        └── MicroserviceManagementApplication        # main class
    └──test/java/edu.kit.microservice_management #contains unit tests   
```
