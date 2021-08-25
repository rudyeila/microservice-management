package edu.kit.microservice_management.logic.model.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepositoryMetadata {
    @JsonProperty("project_id")
    private String projectId;

    @JsonProperty("url")
    private String url;

    @JsonProperty("token")
    private String token;

    @JsonProperty("languages")
    private String languages;


    public void setUrl(String url) {
        this.url = url;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public void setLanguages(String languages) {
        this.languages = languages;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getUrl() {
        return url;
    }
    public String getToken() {
        return token;
    }
    public String getLanguages() {
        return languages;
    }
    public String getProjectId() { return projectId; }
}
