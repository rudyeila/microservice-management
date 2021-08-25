package edu.kit.microservice_management.api.controller;

import edu.kit.microservice_management.logic.model.health.Health;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.KubeStateMetric;
import edu.kit.microservice_management.logic.operations.MicroserviceOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import edu.kit.microservice_management.logic.model.Microservice;

import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
public class MicroserviceController implements MicroserviceAPI {


    private MicroserviceOperations microserviceOperations;

    @Autowired
    public MicroserviceController(MicroserviceOperations microserviceOperations) {
        this.microserviceOperations = microserviceOperations;
    }

    public Collection<Microservice> getAllMicroservices() {
        return microserviceOperations.getListOfRegisteredMicroservices();
    }

    public Microservice getSingleMicroservice(@PathVariable String deployment_name) {
        return microserviceOperations.getSingleMicroservice(deployment_name);
    }

    public Health getHealthOfMicroservice(@PathVariable String deployment_name) {
        return microserviceOperations.getHealthMonitorDataOfRegisteredMicroservice(deployment_name);
    }

    @Override
    public List<KubeStateMetric> getMetricsOfMicroservice(@PathVariable String deployment_name) {
        return microserviceOperations.getMetricsOfMicroservice(deployment_name);
    }


}
