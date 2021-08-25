package edu.kit.microservice_management.logic.model.helm;

import java.util.List;

public class HelmChart {
    private String apiVersion;
    private String appVersion;
    private String version;
    private String name;
    private String description;
    private String type;
    private List<HelmDependency> dependencies;

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<HelmDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<HelmDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }
}
