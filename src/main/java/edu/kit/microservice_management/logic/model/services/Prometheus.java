package edu.kit.microservice_management.logic.model.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.kit.microservice_management.infrastructure.connectors.PrometheusCommunication;
import edu.kit.microservice_management.logic.model.kubernetes.Pod;
import edu.kit.microservice_management.logic.model.query.PrometheusQuery;
import edu.kit.microservice_management.logic.model.query.metrics.kube_state.*;
import edu.kit.microservice_management.logic.model.query.selectors.EqualitySelector;
import edu.kit.microservice_management.logic.model.query.selectors.Selector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;

@Service
public class Prometheus {

    private PrometheusCommunication prometheusCommunication;
    private ObjectMapper objectMapper;

    public List<KubeStateMetric> getKubeDeploymentStatusCondition_conditionAvailable_statusTrue() {
        PrometheusQuery query = new PrometheusQuery(
                KubeStateMetric.KUBE_DEPLOYMENT_STATUS_CONDITION,
                new ArrayList<>() {
                    {
                        add(new EqualitySelector("condition", "Available"));
                        add(new EqualitySelector("status", "true"));
                    }
                }
        );
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_DEPLOYMENT_STATUS_CONDITION));
    }

    public List<KubeStateMetric> getKubeDeploymentStatusCondition_conditionProgressing_statusTrue() {
        PrometheusQuery query = new PrometheusQuery(
                KubeStateMetric.KUBE_DEPLOYMENT_STATUS_CONDITION,
                new ArrayList<>() {
                    {
                        add(new EqualitySelector("condition", "Progressing"));
                        add(new EqualitySelector("status", "true"));
                    }
                }
        );
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_DEPLOYMENT_STATUS_CONDITION));
    }

    public List<KubeStateMetric> getKubeDeploymentCreated() {
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_DEPLOYMENT_CREATED);
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_DEPLOYMENT_CREATED));
    }

    public List<KubeStateMetric> getKubeDeploymentSpecPaused() {
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_DEPLOYMENT_SPEC_PAUSED);
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_DEPLOYMENT_SPEC_PAUSED));
    }

    public List<KubeStateMetric> getKubeDeploymentStatusReplicas() {
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_DEPLOYMENT_STATUS_REPLICAS);
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_DEPLOYMENT_SPEC_PAUSED));
    }

    public List<KubeStateMetric> getKubeDeploymentStatusReplicasAvailable() {
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_DEPLOYMENT_STATUS_REPLICAS_AVAILABLE);
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_DEPLOYMENT_STATUS_REPLICAS_AVAILABLE));
    }

    public List<KubeStateMetric> getKubeReplicaSetOwnerMetrics() {
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_REPLICASET_OWNER);
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_REPLICASET_OWNER));
    }


    public List<KubeStateMetric> getKubeReplicaSetStatusReplicasMetrics(boolean activeOnly) {
        String predicate = activeOnly ? "> 0" : "";
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_REPLICASET_STATUS_REPLICAS, (Selector) null, predicate);
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_REPLICASET_STATUS_REPLICAS));
    }

    public List<KubeStateMetric> getKubePodInfoMetrics() {
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_POD_INFO);
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_POD_INFO));
    }

    public List<KubeStateMetric> getKubePodStatusPhaseMetrics() {
        PrometheusQuery query = new PrometheusQuery(KubeStateMetric.KUBE_POD_STATUS_PHASE, (Selector) null, "> 0");
        return this.getMetricsFromQuery(query, KubeStateMetric.getMetricKeys().get(KubeStateMetric.KUBE_POD_STATUS_PHASE));
    }


    public String getPodPhase(String podName) {
        String podPhase = Pod.PHASE_UNKNOWN;
        PrometheusQuery query = new PrometheusQuery("kube_pod_status_phase", new Selector("pod", podName), "> 0");
        JsonNode results = executeAndExtractResultFromQuery(query);
        if (!results.isEmpty()) {
            podPhase = results.get(0).get("metric").get("phase").textValue();
        }
        return podPhase;
    }

    @Autowired
    public void setPrometheusCommunication(PrometheusCommunication prometheusCommunication) {
        this.prometheusCommunication = prometheusCommunication;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JsonNode executeQuery(PrometheusQuery query) {
        JsonNode json = null;
        try {
            json = prometheusCommunication.query(query.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return this.objectMapper.createObjectNode(); // empty node
        }
        return json;
    }

    public JsonNode executeAndExtractResultFromQuery(PrometheusQuery query) {
        JsonNode json = null;
        try {
            json = prometheusCommunication.query(query.toString());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return  this.objectMapper.createObjectNode(); // empty node
        }
        JsonNode results = json.get("data").get("result");
        return results;
    }

    public List<KubeStateMetric> getMetricsFromQuery(PrometheusQuery query, List<String> keys) {
        List<KubeStateMetric> metrics = new ArrayList<>();
        JsonNode results = executeAndExtractResultFromQuery(query);
        if (!results.isEmpty()) {
            for (JsonNode result : results) {
                JsonNode metric = result.get("metric");
                String namespace = metric.get("namespace").textValue();
                String __name__ = metric.get("__name__").textValue();
                JsonNode valueNode = result.get("value");
                int value = Integer.valueOf(valueNode.get(1).textValue()); // for some reason intValue() was always returning 0
                Map<String, String> attributes = new HashMap<>();
                for (String key : keys) {
                    try {
                        attributes.put(key, metric.get(key).textValue());
                    } catch (Exception e) {
                        Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).info(e.toString());
                    }
                }
                metrics.add(new KubeStateMetric(namespace, __name__, value, attributes));
            }
        }
        return metrics;
    }

}
