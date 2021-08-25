package edu.kit.microservice_management.logic.model.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PipelineMetadata {
    private String url;
    private String commitRef;

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("commit_ref")
    public void setCommitRef(String commitRef) {
        this.commitRef = commitRef;
    }


    public String getCommitRef() {
        return commitRef;
    }

    public String getUrl() {
        return url;
    }

}
