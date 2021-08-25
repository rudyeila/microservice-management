package edu.kit.microservice_management.logic.operations;

import edu.kit.microservice_management.logic.model.Microservice;
import edu.kit.microservice_management.logic.model.health.Health;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.KubeStateMetric;

import java.util.Collection;
import java.util.List;

public interface IMicroserviceManagement {

    Collection<Microservice> getListOfRegisteredMicroservices();
    Microservice getSingleMicroservice(String deployment_name);
    Health getHealthMonitorDataOfRegisteredMicroservice(String deployment_name);
    List<KubeStateMetric> getMetricsOfMicroservice(String deployment_name);
}
