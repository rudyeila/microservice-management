package edu.kit.microservice_management.logic.model.kubernetes;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class Pod {

    private String name;
    public String replicaSetName;
    private String phase;

    public static final String PHASE_RUNNING = "Running";
    public static final String PHASE_SUCCEEDED = "Succeeded";
    public static final String PHASE_PENDING = "Pending";
    public static final String PHASE_FAILED = "Failed";
    public static final String PHASE_UNKNOWN = "Unknown";

    @ConstructorProperties({"podName", "replicaSetName", "phase"})
    public Pod(String name, String replicaSetName, String phase) {
        this.name = name;
        this.replicaSetName = replicaSetName;
        this.phase = phase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getReplicaSetName() {
        return replicaSetName;
    }

    public void setReplicaSetName(String replicaSetName) {
        this.replicaSetName = replicaSetName;
    }

    public boolean isHealthy() {
        return phase.equals(Pod.PHASE_RUNNING) || phase.equals(Pod.PHASE_SUCCEEDED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pod pod = (Pod) o;
        return name.equals(pod.name) &&
                replicaSetName.equals(pod.replicaSetName) &&
                phase.equals(pod.phase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, replicaSetName, phase);
    }
}
