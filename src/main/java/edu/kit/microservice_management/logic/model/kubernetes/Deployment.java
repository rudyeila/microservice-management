package edu.kit.microservice_management.logic.model.kubernetes;

import edu.kit.microservice_management.logic.model.query.metrics.kube_state.KubeStateMetric;
import edu.kit.microservice_management.logic.model.services.Prometheus;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Deployment {

    private String name;
    private Collection<ReplicaSet> activeReplicaSets;
    private List<KubeStateMetric> deploymentMetrics;

    public Deployment(String name, Collection<ReplicaSet> activeReplicaSets) {
        this.name = name;
        this.activeReplicaSets = activeReplicaSets;
        this.deploymentMetrics = new ArrayList<>();
    }

    @ConstructorProperties({"name", "activeReplicaSets", "deploymentMetrics"})
    public Deployment(String name, Collection<ReplicaSet> activeReplicaSets, List<KubeStateMetric> deploymentMetrics) {
        this.name = name;
        this.activeReplicaSets = activeReplicaSets;
        this.deploymentMetrics = deploymentMetrics;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<ReplicaSet> getActiveReplicaSets() {
        return activeReplicaSets;
    }

    public void setActiveReplicaSets(Collection<ReplicaSet> activeReplicaSets) {
        this.activeReplicaSets = activeReplicaSets;
    }

    public List<KubeStateMetric> getDeploymentMetrics() {
        return deploymentMetrics;
    }

    public void setDeploymentMetrics(List<KubeStateMetric> deploymentMetrics) {
        this.deploymentMetrics = deploymentMetrics;
    }
}
