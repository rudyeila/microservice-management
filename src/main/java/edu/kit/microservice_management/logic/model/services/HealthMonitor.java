package edu.kit.microservice_management.logic.model.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.microservice_management.logic.model.health.Condition;
import edu.kit.microservice_management.logic.model.health.Health;
import edu.kit.microservice_management.logic.model.kubernetes.Deployment;
import edu.kit.microservice_management.logic.model.kubernetes.Pod;
import edu.kit.microservice_management.logic.model.health.State;
import edu.kit.microservice_management.logic.model.kubernetes.ReplicaSet;
import edu.kit.microservice_management.logic.model.query.KubernetesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;

@Component
public class HealthMonitor {

    private Prometheus prometheus;

    @Autowired
    public HealthMonitor(Prometheus prometheus){
        this.prometheus = prometheus;
    }

    public Health getHealth(Deployment deployment) {
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(deployment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Collection<Pod> pods = new ArrayList<>();
        for (ReplicaSet rs : deployment.getActiveReplicaSets()) {
            pods.addAll(rs.getPods());
        }
        /* No pods were found for the deployment so we can assume it is not deployed */
        if (pods.isEmpty()) {
            return new Health(State.DOWN, Condition.UNKNOWN, new ArrayList<>(), deployment);
        }

        return new Health(pods, deployment);
    }

}
