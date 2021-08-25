# **`MicroserviceManagement`** Application Context Sharing View

![Application Context Sharing View](../figures/microservice_management_sv.svg)

The central task of the application context MicroserviceManagement is to execute the service registry task and provide monitor health data about registered microservices. Therefore, this application context uses the external server as a source for runtime data of microservices sharing the entity Health. It also uses the bounded context Service to register/unregister microservices through the shared entity Microservice.