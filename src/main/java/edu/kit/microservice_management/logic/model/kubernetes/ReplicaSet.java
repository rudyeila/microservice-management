package edu.kit.microservice_management.logic.model.kubernetes;

import java.beans.ConstructorProperties;
import java.util.Collection;
import java.util.List;

public class ReplicaSet {
    private String name;
    private String deploymentName;
    private boolean isActive;
    private List<Pod> pods;

    @ConstructorProperties({"name", "deploymentName", "pods", "isActive"})
    public ReplicaSet(String name, String deploymentName, List<Pod> pods, boolean isActive) {
        this.name = name;
        this.deploymentName = deploymentName;
        this.pods = pods;
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public List<Pod> getPods() {
        return pods;
    }

    public void setPods(List<Pod> pods) {
        this.pods = pods;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
