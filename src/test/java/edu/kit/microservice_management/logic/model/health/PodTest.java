package edu.kit.microservice_management.logic.model.health;

import edu.kit.microservice_management.logic.model.kubernetes.Pod;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PodTest {

    @Test
    void testRunningIsHealthy() {
        Pod pod = new Pod("podName", "replicaSetName", Pod.PHASE_RUNNING);
        boolean isHealthy = pod.isHealthy();
        assertEquals(true, isHealthy);
    }

    @Test
    void testSucceededIsHealthy() {
        Pod pod = new Pod("podName", "replicaSetName", Pod.PHASE_SUCCEEDED);
        boolean isHealthy = pod.isHealthy();
        assertEquals(true, isHealthy);
    }

    @Test
    void testFailedIsUnhealthy() {
        Pod pod = new Pod("podName", "replicaSetName", Pod.PHASE_FAILED);
        boolean isHealthy = pod.isHealthy();
        assertEquals(false, isHealthy);
    }

    @Test
    void testPendingIsUnhealthy() {
        Pod pod = new Pod("podName", "replicaSetName", Pod.PHASE_PENDING);
        boolean isHealthy = pod.isHealthy();
        assertEquals(false, isHealthy);
    }

    @Test
    void testUnknownIsUnhealthy() {
        Pod pod = new Pod("podName", "replicaSetName", Pod.PHASE_UNKNOWN);
        boolean isHealthy = pod.isHealthy();
        assertEquals(false, isHealthy);
    }

}
