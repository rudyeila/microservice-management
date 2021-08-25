# Installation
1. Clone repository: https://git.scc.kit.edu/cm-tm/cm-team/2-4.serviceenvironment/developerportal/service_registry.git
2. Make "maven update" to get all dependencies
3. Run main class or create executable jar with "maven package"

## Configuration
For configuration the microservice requires the following environment variables:

| Variable       | Description            | Default |
| -------------- | ---------------------- | ------- |
| DOMAIN_SERVICE_URL | Url of the Service |         |
| HEALTH_PROMETHEUS_URL | Url of the Prometheus Server   |        |
