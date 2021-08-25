## API Specification 

Method  | Endpoint                  | Request Body      | Response          | Description
---     | ---                       | ---               | ---               | ---               
POST    | /register                 | CompileTimeData   | success/failure   | Register new release
POST    | /unregister               | CompileTimeData   | success/failure   | Unregister existing release
GET     | /microservices               | -   | registered microservices    | Get list of all registered microservices
GET     | /microservice/{deployment_name}               | -   | registered microservice/failure    | Get information about registered microservice
GET     | /microservice/{deployment_name}/metrics                     | -   | RunTimeData/failure   | Get metrics data of specific microservice
GET     | /microservice/{deployment_name}/health                     | -   | Health/failure   | Get health of specific microservice