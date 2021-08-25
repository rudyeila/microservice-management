package edu.kit.microservice_management.logic.model.query;

import edu.kit.microservice_management.logic.model.kubernetes.Deployment;
import edu.kit.microservice_management.logic.model.kubernetes.Pod;
import edu.kit.microservice_management.logic.model.kubernetes.ReplicaSet;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.*;
import edu.kit.microservice_management.logic.model.services.Prometheus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class KubernetesMapper {

    private Prometheus prometheus;

    private List<KubeStateMetric> kube_replicaset_owner;
    private List<KubeStateMetric> active_kube_replicaset_owner;
    private List<KubeStateMetric> kube_pod_info;
    private List<KubeStateMetric> kube_pod_status_phase;
    private List<KubeStateMetric> kube_deployment_status_condition_available;
    private List<KubeStateMetric> kube_deployment_status_condition_progressing;
    private List<KubeStateMetric> kube_deployment_created;
    private List<KubeStateMetric> kube_deployment_spec_paused;
    private List<KubeStateMetric> kube_deployment_status_replicas;
    private List<KubeStateMetric> kube_deployment_status_replicas_available;


    private List<ReplicaSet> activeReplicaSets;
    private List<Pod> pods;

    @Autowired
    public KubernetesMapper(Prometheus prometheus) {
        this.prometheus = prometheus;

        this.kube_replicaset_owner = prometheus.getKubeReplicaSetOwnerMetrics();
        this.active_kube_replicaset_owner = this.getActiveReplicaSetsMetrics();
        this.kube_pod_info = prometheus.getKubePodInfoMetrics();
        this.kube_pod_status_phase = prometheus.getKubePodStatusPhaseMetrics();
        this.kube_deployment_status_condition_available = prometheus.getKubeDeploymentStatusCondition_conditionAvailable_statusTrue();
        this.kube_deployment_status_condition_progressing = prometheus.getKubeDeploymentStatusCondition_conditionProgressing_statusTrue();
        this.kube_deployment_created = prometheus.getKubeDeploymentCreated();
        this.kube_deployment_spec_paused = prometheus.getKubeDeploymentSpecPaused();
        this.kube_deployment_status_replicas = prometheus.getKubeDeploymentStatusReplicas();
        this.kube_deployment_status_replicas_available = prometheus.getKubeDeploymentStatusReplicasAvailable();

        // Construct Pod objects from metrics
        this.pods = new ArrayList<>();
        if (kube_pod_info.size() == kube_pod_status_phase.size()) {
            for (int i = 0; i < kube_pod_info.size(); i++) {
                KubeStateMetric kubePodInfoMetric = kube_pod_info.get(i);
                KubeStateMetric kubePodStatusMetric = kube_pod_status_phase.get(i);
                pods.add(new Pod(kubePodInfoMetric.getAttributes().get("pod"),
                        kubePodInfoMetric.getAttributes().get("created_by_name"),
                        kubePodStatusMetric.getAttributes().get("phase")));
            }
        }

        this.activeReplicaSets = new ArrayList<>();
        for (KubeStateMetric replicaSetOwnerMetric : this.active_kube_replicaset_owner) {
            ReplicaSet rs = new ReplicaSet(
                    replicaSetOwnerMetric.getAttributes().get("replicaset"),
                    replicaSetOwnerMetric.getAttributes().get("owner_name"),
                    this.getReplicaSetPods(replicaSetOwnerMetric.getAttributes().get("replicaset")),
                    true);
            this.activeReplicaSets.add(rs);
        }
    }

    public Deployment getDeployment(String deployment_name) {
        List<ReplicaSet> deploymentReplicaSets = this.activeReplicaSets.stream()
                .filter(rs -> rs.getDeploymentName().equals(deployment_name)).collect(Collectors.toList());

        List<KubeStateMetric> deploymentMetrics = getDeploymentMetrics(deployment_name);
        Deployment deployment = new Deployment(deployment_name, deploymentReplicaSets, deploymentMetrics);
        return  deployment;
    }

    private List<KubeStateMetric> getDeploymentMetrics(String deployment_name) {
        List<KubeStateMetric> metrics = new ArrayList<>();
        metrics.addAll(this.kube_deployment_status_condition_available.stream()
                 .filter(metric -> metric.getAttributes().get("deployment").equals(deployment_name)).collect(Collectors.toList())
        );

        metrics.addAll(this.kube_deployment_status_condition_progressing.stream()
                .filter(metric -> metric.getAttributes().get("deployment").equals(deployment_name)).collect(Collectors.toList())
        );

        metrics.addAll(this.kube_deployment_created.stream()
                .filter(metric -> metric.getAttributes().get("deployment").equals(deployment_name)).collect(Collectors.toList())
        );

        metrics.addAll(this.kube_deployment_spec_paused.stream()
                .filter(metric -> metric.getAttributes().get("deployment").equals(deployment_name)).collect(Collectors.toList())
        );

        metrics.addAll(this.kube_deployment_status_replicas.stream()
                .filter(metric -> metric.getAttributes().get("deployment").equals(deployment_name)).collect(Collectors.toList())
        );

        metrics.addAll(this.kube_deployment_status_replicas_available.stream()
                .filter(metric -> metric.getAttributes().get("deployment").equals(deployment_name)).collect(Collectors.toList())
        );

        return metrics;
    }

    private List<KubeStateMetric> getReplicaSetMetrics(String replicasetName) {
        List<KubeStateMetric> metrics = new ArrayList<>();

        metrics.addAll(this.active_kube_replicaset_owner.stream()
                .filter(metric -> metric.getAttributes().get("replicaset").equals(replicasetName)).collect(Collectors.toList())
        );

        return metrics;
    }


    private List<KubeStateMetric> getPodMetrics(String podName) {
        List<KubeStateMetric> metrics = new ArrayList<>();

        metrics.addAll(this.kube_pod_info.stream()
                .filter(metric -> metric.getAttributes().get("pod").equals(podName)).collect(Collectors.toList())
        );

        metrics.addAll(this.kube_pod_status_phase.stream()
                .filter(metric -> metric.getAttributes().get("pod").equals(podName)).collect(Collectors.toList())
        );

        return metrics;
    }

    private List<Pod> getReplicaSetPods(String replicaSetName) {
        return this.pods.stream()
                .filter(pod -> pod.getReplicaSetName().equals(replicaSetName)).collect(Collectors.toList());
    }

    private List<KubeStateMetric> getActiveReplicaSetsMetrics() {
        List<KubeStateMetric> kube_replicaset_status_replicas = prometheus.getKubeReplicaSetStatusReplicasMetrics(true);
        return kube_replicaset_owner.stream()
                .filter(rs -> kube_replicaset_status_replicas.stream()
                        .anyMatch(active_rs -> rs.getAttributes().get("replicaset").equals(active_rs.getAttributes().get("replicaset")))
                ).collect(Collectors.toList());
    }


}

