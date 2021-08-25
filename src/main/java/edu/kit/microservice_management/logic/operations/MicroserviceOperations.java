package edu.kit.microservice_management.logic.operations;

import edu.kit.microservice_management.logic.model.health.Health;
import edu.kit.microservice_management.logic.model.kubernetes.Deployment;
import edu.kit.microservice_management.logic.model.query.KubernetesMapper;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.KubeStateMetric;
import edu.kit.microservice_management.logic.model.services.HealthMonitor;
import edu.kit.microservice_management.logic.model.services.DomainService;
import edu.kit.microservice_management.logic.model.Microservice;
import edu.kit.microservice_management.logic.model.services.Prometheus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MicroserviceOperations implements IMicroserviceManagement {

    private DomainService domainService;
    private HealthMonitor healthMonitor;
    private Prometheus prometheus;

    @Autowired
    public MicroserviceOperations(DomainService domainService, HealthMonitor healthMonitor, Prometheus prometheus) {
        this.domainService = domainService;
        this.healthMonitor = healthMonitor;
        this.prometheus = prometheus;
    }

    @Override
    public Collection<Microservice> getListOfRegisteredMicroservices() {
        Collection<Microservice> microservices = domainService.getMicroservices();
        KubernetesMapper kubernetesMapper = new KubernetesMapper(prometheus);
        for (Microservice ms : microservices) {
            Deployment deployment = kubernetesMapper.getDeployment(ms.getHostname());
            Health health = healthMonitor.getHealth(deployment);
            ms.setHealth(health);
        }
        return microservices;
    }

    @Override
    public Microservice getSingleMicroservice(String deployment_name) {
        Microservice microservice = domainService.getSingleMicroservice(deployment_name);
        KubernetesMapper kubernetesMapper = new KubernetesMapper(prometheus);
        Deployment deployment = kubernetesMapper.getDeployment(deployment_name);
        microservice.setHealth(healthMonitor.getHealth(deployment));
        return microservice;
    }



    @Override
    public Health getHealthMonitorDataOfRegisteredMicroservice(String deployment_name) {
        KubernetesMapper kubernetesMapper = new KubernetesMapper(prometheus);
        Deployment deployment = kubernetesMapper.getDeployment(deployment_name);
        return healthMonitor.getHealth(deployment);
    }

    @Override
    public List<KubeStateMetric> getMetricsOfMicroservice(String deployment_name) {
        KubernetesMapper kubernetesMapper = new KubernetesMapper(prometheus);
        Deployment deployment = kubernetesMapper.getDeployment(deployment_name);
        return deployment.getDeploymentMetrics();
    }


}
