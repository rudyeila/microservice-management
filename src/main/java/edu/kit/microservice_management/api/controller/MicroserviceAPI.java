package edu.kit.microservice_management.api.controller;

import edu.kit.microservice_management.logic.model.Microservice;
import edu.kit.microservice_management.logic.model.health.Health;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.KubeStateMetric;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;

@RequestMapping
public interface MicroserviceAPI {

    @GetMapping("/microservices")
    Collection<Microservice> getAllMicroservices();

    @GetMapping("/microservice/{deployment_name}")
    Microservice getSingleMicroservice(@PathVariable String deployment_name);

    @GetMapping("/microservice/{deployment_name}/health")
    Health getHealthOfMicroservice(@PathVariable String deployment_name);

    @GetMapping("/microservice/{deployment_name}/metrics")
    List<KubeStateMetric> getMetricsOfMicroservice(@PathVariable String deployment_name);

}
