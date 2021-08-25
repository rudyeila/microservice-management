package edu.kit.microservice_management.logic.model.health;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.microservice_management.logic.model.kubernetes.Deployment;
import edu.kit.microservice_management.logic.model.kubernetes.Pod;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.KubeStateMetric;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class HealthTest {

    public ObjectMapper mapper = new ObjectMapper();
    public final String deploymentJsonPath = "src/test/java/edu/kit/microservice_management/logic/model/health/deployment.json";

    @Test
    void testDetermineHealthHealthy() {
        Deployment deployment = null;
        try {
            deployment = mapper.readValue(Paths.get(this.deploymentJsonPath).toFile(), Deployment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pod ph = new Pod("pod1", "replicaSet1", Pod.PHASE_RUNNING);
        Collection<Pod> pods = new ArrayList<>();

        pods.add(ph);
        Health health = new Health(pods, deployment);

        assertEquals(health.getState(), State.UP);
        assertEquals(health.getCondition(), Condition.STABLE);
    }

    @Test
    void testDetermineHealthUpAndUnstable() {
        Deployment deployment = null;
        try {
            deployment = mapper.readValue(Paths.get(this.deploymentJsonPath).toFile(), Deployment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (KubeStateMetric metric : deployment.getDeploymentMetrics()) {
            metric.setValue(0);
        }

        Pod ph1 = new Pod("pod1", "replicaSet1", Pod.PHASE_RUNNING);
        Pod ph2 = new Pod("pod2", "replicaSet2", Pod.PHASE_FAILED);
        Collection<Pod> pods = new ArrayList<>();
        pods.add(ph1);
        pods.add(ph2);
        Health health = new Health(pods, deployment);

        assertEquals(health.getState(), State.UP);
        assertEquals(health.getCondition(), Condition.UNSTABLE);
    }

    @Test
    void testDetermineHealthDown() {
        Deployment deployment = null;
        try {
            deployment = mapper.readValue(Paths.get(this.deploymentJsonPath).toFile(), Deployment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pod ph1 = new Pod("pod1", "replicaSet1", Pod.PHASE_PENDING);
        Pod ph2 = new Pod("pod2", "replicaSet2", Pod.PHASE_FAILED);
        Collection<Pod> pods = new ArrayList<>();
        pods.add(ph1);
        pods.add(ph2);

        Health health = new Health(pods, deployment);

        assertEquals(State.DOWN, health.getState());
        assertEquals(Condition.UNSTABLE, health.getCondition());
    }

    @Test
    void testDetermineHealthDownNoPods() {
        Deployment deployment = null;
        try {
            deployment = mapper.readValue(Paths.get(this.deploymentJsonPath).toFile(), Deployment.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collection<Pod> pods = new ArrayList<>();

        Health health = new Health(pods, deployment);

        assertEquals(health.getState(), State.DOWN);
        assertEquals(health.getCondition(), Condition.UNKNOWN);
    }
}