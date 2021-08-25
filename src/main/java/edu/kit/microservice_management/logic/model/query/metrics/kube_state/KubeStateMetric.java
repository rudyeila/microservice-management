package edu.kit.microservice_management.logic.model.query.metrics.kube_state;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KubeStateMetric {

    private String namespace;
    private String __name__;
    protected int value;
    private Map<String, String> attributes;


    // Deployment metrics
    public static final String KUBE_DEPLOYMENT_STATUS_CONDITION = "kube_deployment_status_condition";
    public static final String KUBE_DEPLOYMENT_CREATED = "kube_deployment_created";
    public static final String KUBE_DEPLOYMENT_SPEC_PAUSED = "kube_deployment_spec_paused";
    public static final String KUBE_DEPLOYMENT_STATUS_REPLICAS = "kube_deployment_status_replicas";
    public static final String KUBE_DEPLOYMENT_STATUS_REPLICAS_AVAILABLE = "kube_deployment_status_replicas_available";

    // Replicaset metrics
    public static final String KUBE_REPLICASET_OWNER = "kube_replicaset_owner";
    public static final String KUBE_REPLICASET_STATUS_REPLICAS = "kube_replicaset_status_replicas";

    // Pod metrics
    public static final String KUBE_POD_INFO = "kube_pod_info";
    public static final String KUBE_POD_STATUS_PHASE = "kube_pod_status_phase";


    public static Map<String, List<String>> getMetricKeys() {
        return metricKeys;
    }

    private static final Map<String, List<String>> metricKeys = new HashMap<>(){{
        // Deployment metrics
        put(KUBE_DEPLOYMENT_STATUS_CONDITION, new ArrayList<>(){{
            add("deployment");
            add("condition");
            add("status");
        }});

        put(KUBE_DEPLOYMENT_CREATED, new ArrayList<>(){{
            add("deployment");
        }});

        put(KUBE_DEPLOYMENT_SPEC_PAUSED, new ArrayList<>(){{
            add("deployment");
        }});

        put(KUBE_DEPLOYMENT_STATUS_REPLICAS, new ArrayList<>(){{
            add("deployment");
        }});

        put(KUBE_DEPLOYMENT_STATUS_REPLICAS_AVAILABLE, new ArrayList<>(){{
            add("deployment");
        }});

        // Replicaset metrics
        put(KUBE_REPLICASET_OWNER, new ArrayList<>(){{
            add("replicaset");
            add("owner_name");
            add("owner_kind");
            add("owner_is_controller");
        }});

        put(KUBE_REPLICASET_STATUS_REPLICAS, new ArrayList<>(){{
            add("replicaset");
        }});

        // Pod metrics
        put(KUBE_POD_INFO, new ArrayList<>(){{
            add("pod");
            add("uid");
            add("pod_ip");
            add("node");
            add("host_network");
            add("host_ip");
            add("created_by_name");
            add("created_by_kind");
        }});

        put(KUBE_POD_STATUS_PHASE, new ArrayList<>(){{
            add("pod");
            add("phase");
        }});

    }};


    @ConstructorProperties({"namespace", "__name__", "value", "attributes"})
    public KubeStateMetric(String namespace, String __name__, int value, Map<String, String> attributes) {
        this.namespace = namespace;
        this.__name__ = __name__;
        this.value = value;
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String get__name__() {
        return __name__;
    }

    public void set__name__(String __name__) {
        this.__name__ = __name__;
    }

    @JsonAnyGetter
    public Map<String, String> getAttributes() {
        return attributes;
    }

    @JsonAnySetter
    public void add(String key, String value) {
        attributes.put(key, value);
    }

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
