Disclaimer: Not everything in this repository was done by me, but I had a large contribution to it. On the API level, I wrote the Microservice API. Furthermore, I contributed with everything related to Prometheus, Kubernetes, and health of the microservicess 

# MicroserviceManagement Application Microservice

MicroserviceManagement gets the CompileTimeData from the [RegisterStep](https://git.scc.kit.edu/cm-tm/cm-team/2-4.serviceenvironment/developerportal/pipelinestep/1.pipelinestep) including a GitLab repository access token.
Then it receives all the necessary information and creates an object of type microservice.
Once the object is created successfully, MicroserviceManagement stores this object in the domain microservice [Service](https://git.scc.kit.edu/cm-tm/cm-team/2-4.serviceenvironment/1.domain/service). Microservices can be monitored once registered. Runtime data about a microservice is fetched and used as an object Metric and passed to the frontend through an API endpoint.

## Design

[Application Context Sharing View](pages/application_context_sharing_view.md)

[API Specification](pages/api.md) 

[Task Processes](pages/task_processes.md) 

## Implementation and Tests

 [Introduction](pages/introduction.md)

 [Local Installation](pages/installation.md)

 [DevOps Template Configuration](pages/devops.md)

 [Unit Tests](pages/unit_tests.md)

 [Integration Tests](pages/integration_tests.md)

 [End-to-End Tests](pages/end-to-end-tests.md) 


## Organization

*Documentation Template Version 1.0.0*

| Documentation Responsible | Start | End  |
| ------------------------- | ----- | ---- |
| tbd                      | tbd   | tbd |
