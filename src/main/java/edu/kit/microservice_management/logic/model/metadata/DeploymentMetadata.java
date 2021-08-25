package edu.kit.microservice_management.logic.model.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeploymentMetadata {
    @JsonProperty("release")
    private String release;

    @JsonProperty("service_url")
    private String serviceUrl;

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}
