package edu.kit.microservice_management.logic.model.metadata;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GitLabMetadata {
    @JsonProperty("pipeline")
    private PipelineMetadata pipelineMetadata;

    @JsonProperty("repository")
    private RepositoryMetadata repositoryMetadata;

    @JsonProperty("deployment")
    private DeploymentMetadata deploymentMetadata;


    public PipelineMetadata getPipelineMetadata() {
        return pipelineMetadata;
    }

    public void setPipelineMetadata(PipelineMetadata pipelineMetadata) {
        this.pipelineMetadata = pipelineMetadata;
    }

    public RepositoryMetadata getRepositoryMetadata() {
        return repositoryMetadata;
    }

    public void setRepositoryMetadata(RepositoryMetadata repositoryMetadata) {
        this.repositoryMetadata = repositoryMetadata;
    }

    public DeploymentMetadata getDeploymentMetadata() {
        return deploymentMetadata;
    }

    public void setDeploymentMetadata(DeploymentMetadata deploymentMetadata) {
        this.deploymentMetadata = deploymentMetadata;
    }
}
