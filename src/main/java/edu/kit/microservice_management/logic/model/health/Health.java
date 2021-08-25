package edu.kit.microservice_management.logic.model.health;

import edu.kit.microservice_management.logic.model.kubernetes.Deployment;
import edu.kit.microservice_management.logic.model.kubernetes.Pod;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.KubeStateMetric;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Collection;

public class Health {

    private State state = State.UNKNOWN;
    private Condition condition = Condition.UNKNOWN;
    private Collection<Pod> pods;
    private Deployment deployment;

    @ConstructorProperties({"state", "condition", "pods","deployment"})
    public Health(State state, Condition condition, Collection<Pod> pods, Deployment deployment) {
        this.state = state;
        this.condition = condition;
        this.pods = pods;
        this.deployment = deployment;
    }

    public Health(Collection<Pod> pods, Deployment deployment) {
        this.pods = pods;
        this.deployment = deployment;
        this.determineHealth();
    }


    public Health() {
        this.pods = new ArrayList<>();
        this.deployment = new Deployment("No deployment found", new ArrayList<>());
        setState(State.DOWN);
    }

    public void determineHealth() {
        if (this.pods.isEmpty()) {
            setState(State.DOWN);
            setCondition(Condition.UNKNOWN);
            return;
        }

        boolean hasHealthyPods = false;
        for (Pod pod : this.pods) {
            if (pod.isHealthy()){
                hasHealthyPods = true;
                break;
            }
        }

        /* If a deployment has multiple pods:
         *  It is considered UP if at least one pod is running/succeeded
         **/
        if (hasHealthyPods) {
            setState(State.UP);
            setCondition(this.determineCondition());
        } else {
            setState(State.DOWN);
            setCondition(Condition.UNSTABLE);
        }
    }

    private Condition determineCondition() {
        Condition condition;

        boolean statusConditionOk = false;
        boolean createdOk = false;
        boolean specPausedOk = false;
        boolean statusReplicasOk = false;
        boolean statusReplicasAvailableOk = false;


        for (KubeStateMetric metric : this.deployment.getDeploymentMetrics()) {
            String metricName = metric.get__name__();
            int value = metric.getValue();

            switch (metricName) {
                case KubeStateMetric.KUBE_DEPLOYMENT_STATUS_CONDITION:
                    if (value >= 1) {
                        statusConditionOk = true;
                    }
                    break;
                case KubeStateMetric.KUBE_DEPLOYMENT_CREATED:
                    if (metric.getValue() >= 0) {
                        createdOk = true;
                    }
                    break;
                case KubeStateMetric.KUBE_DEPLOYMENT_SPEC_PAUSED:
                    if (metric.getValue() == 0) {
                        specPausedOk = true;
                    }
                    break;
                case KubeStateMetric.KUBE_DEPLOYMENT_STATUS_REPLICAS:
                    if (metric.getValue() >= 1) {
                        statusReplicasOk = true;
                    }
                    break;
                case KubeStateMetric.KUBE_DEPLOYMENT_STATUS_REPLICAS_AVAILABLE:
                    if (metric.getValue() >= 1) {
                        statusReplicasAvailableOk = true;
                    }
                    break;
            }
        }

        condition = (statusConditionOk && createdOk && specPausedOk && statusReplicasOk && statusReplicasAvailableOk) ?
                Condition.STABLE :
                Condition.UNSTABLE;

        return  condition;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Collection<Pod> getPods() {
        return pods;
    }

    public void setPods(Collection<Pod> pods) {
        this.pods = pods;
        this.determineHealth();
    }
}
