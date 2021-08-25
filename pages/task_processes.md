# Task Processes

## Register a Microservice

![Task Process Register Microservice](../figures/tp/register_tp.svg)

/1/ The pipeline is extended with a register step after the deployment.

/2/ The register step creates a POST request with the compile-time data to the ServiceRegistry.

/3/ The ServiceRegistry validates the input by checking if the data has the correct format.

/4.1/ In case the compile-time data has the correct format a success response is sent to the pipeline step.

/4.2/ In case the compile-time data has a wrong format, an error message is sent to the pipeline step causing the pipeline to display a failure.

/5.1/ Using the URL and token from the compile-time data, the repository is fetched.

/5.2/ On success, the compile-time data and the repository are used to create the microservice entity.

/5.3/ In case the fetching of the repository does not work, the process is aborted and an error sent back to the pipeline.

/6/ To check if a microservice already exists, a GET request with the hostname is sent to the Service domain service.

/7/ The Service responds with the microservice matching the hostname or an error message if no match was found.

/8.1/ If a microservice got returned, this microservice is updated.

/9/ The updated microserive is sent back to the Service.

/8.2/ If no microservice with that hostname exists yet, a new microservice is created.

/10/ The new microserive is sent to the domain service.

## Unregister a Microservice

![Deregister a Microservice](../figures/tp/unregister_tp.svg)

/1/ The undeploy step is triggered.

/2/ The POST requrest with the hostname is send to the ServiceRegistry.

/3/ The input is then validated.

/4.1/ If an valid hostname has been provided, microservice is deleted.

/4.2/ If an invalid hostname has been provided, an error message is sent to the client.

/5.1/ A POST request with the hostname is sent to the Service domain service.

/5.2/ If an error occurs, an error message is sent to the client.

## Get List of Registered Microservices

![Get list of registered microservices](../figures/tp/get_registered_microservices_tp.svg)

/1/ The application microservice receives a GET request with the hostname for the list of registered microservices.

/2/ Fetch list of microservices from the domain microservice Service.

/3/ Receive list of registered microservices.

/4/ Fetch relevant metrics from the Prometheus server

/5/ Receive list of metrics and use them to determine the health of each microservice

/6/ The list of registered microservices appended with health information and is sent back to the application.

## Get a Single Registered Microservice

![DGet compile-time data of registered microservice](../figures/tp/get_single_registered_microservice.svg)

/1/ Validate the parameter deployment_name from incoming GET request.

/2.1/ If input is valid, get microservice data from the domain microservice Service.

/2.2/ If input is invalid, send bad request response to application.

/3/ Send GET request with the deplyoment_name parameter to domain microservice Service to fetch the microservice.

/4/ Receive response as a Microservice entity.

/5/ Fetch relevant metrics from the Prometheus server

/6/ Receive metrics from Prometheus server and determine health of microservice based on the metrics

/7/ Append health information to the microservice entity and send it back to application.

## Get Health Monitor Data of Registered Microservice

![Get health monitor data of registered microservice](../figures/tp/monitor_tp.svg)

/1/ Validate the parameter deployment_name from incoming GET request.

/2.1/ If input is valid, get health monitor data from the external API Prometheus.

/2.2/ If input is invalid, send bad request response to application.

/3/ Send GET request to API Prometheus to fetch metrics data.

/4/ Receive response in form of metrics and construct Health based on the metrics.

/5/ Send Health back to application.

## Get Metrics of a Registered Microservice

![Get metrics of a registered microservice](../figures/tp/get_metrics.svg)

/1/ Validate the parameter deployment_name from incoming GET request.

/2.1/ If input is valid, get health monitor data from the external API Prometheus.

/2.2/ If input is invalid, send bad request response to application.

/3/ Send GET request to API Prometheus to fetch metrics data.

/4/ Receive response in form of metrics.

/5/ Send list of metrics back to application.
