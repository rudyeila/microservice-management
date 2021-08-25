package edu.kit.microservice_management.logic.model;

import edu.kit.microservice_management.infrastructure.connectors.RepositoryCommunication;
import edu.kit.microservice_management.infrastructure.configuration.Constants;
import edu.kit.microservice_management.infrastructure.utils.json.JsonConverter;
import edu.kit.microservice_management.infrastructure.utils.logging.Logger;
import edu.kit.microservice_management.logic.model.health.Health;
import edu.kit.microservice_management.logic.model.kubernetes.Deployment;
import edu.kit.microservice_management.logic.model.query.KubernetesMapper;
import edu.kit.microservice_management.logic.model.services.HealthMonitor;
import edu.kit.microservice_management.logic.model.helm.HelmChart;
import edu.kit.microservice_management.logic.model.helm.HelmDependency;
import edu.kit.microservice_management.logic.model.metadata.DeploymentMetadata;
import edu.kit.microservice_management.logic.model.metadata.PipelineMetadata;
import edu.kit.microservice_management.logic.model.metadata.RepositoryMetadata;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
public class MicroserviceBuilder {
    private RepositoryCommunication repositoryCommunication;
    private JsonConverter jsonConverter;
    private HealthMonitor healthMonitor;
    private KubernetesMapper kubernetesMapper;

    @Autowired
    public MicroserviceBuilder(RepositoryCommunication repositoryCommunication, JsonConverter jsonConverter, HealthMonitor healthMonitor, KubernetesMapper kubernetesMapper) {
        this.repositoryCommunication = repositoryCommunication;
        this.jsonConverter = jsonConverter;
        this.healthMonitor = healthMonitor;
        this.kubernetesMapper = kubernetesMapper;
    }

    public Microservice createMicroservice(PipelineMetadata pipelineMetadata, RepositoryMetadata repositoryMetadata,
                                           DeploymentMetadata deploymentMetadata) {
        String projectId = repositoryMetadata.getProjectId();
        String token = repositoryMetadata.getToken();
        String hostname = getHostname(deploymentMetadata);
        String version = getVersion(pipelineMetadata);
        String address = getAddress(deploymentMetadata);
        String codeOwner = getCodeOwner(projectId, token);
        List<HelmDependency> dependencies = getDependencies(projectId, token);
        List<Link> links = getLinks(repositoryMetadata, pipelineMetadata, deploymentMetadata, projectId, token);
        Deployment deployment = kubernetesMapper.getDeployment(hostname);
        Health health = healthMonitor.getHealth(deployment);
        Microservice microservice = new Microservice(hostname, version, address, codeOwner, dependencies, links, health);
        return microservice;
    }

    private String getHostname(DeploymentMetadata deploymentMetadata) {
        String release = deploymentMetadata.getRelease();
        return release;
    }

    private String getVersion(PipelineMetadata pipelineMetadata) {
        String branchName = pipelineMetadata.getCommitRef();
        return branchName;
    }

    private String getCodeOwner(String projectId, String token) {
        String rawCodeOwnersFileContent = repositoryCommunication.getCodeOwnersContentFromClient(projectId, token);
        String codeOwnersWithoutLastNewLine = StringUtils.chomp(rawCodeOwnersFileContent);
        return codeOwnersWithoutLastNewLine;
    }

    private List<Link> getLinks(RepositoryMetadata repositoryMetadata, PipelineMetadata pipelineMetadata,
                                DeploymentMetadata deploymentMetadata, String projectId, String token) {
        Link repositoryLink = new Link(Constants.REPOSITORY_LINK_NAME, repositoryMetadata.getUrl());
        Link pipelineLink = new Link(Constants.PIPELINE_LINK_NAME, pipelineMetadata.getUrl());
        Link apiLink = new Link(Constants.API_LINK_NAME, repositoryCommunication.getApiLink(repositoryMetadata, projectId, token));
        Link swaggerLink = new Link(Constants.SWAGGER_LINK_NAME, repositoryCommunication.getSwaggerLink(deploymentMetadata));
        LinkedList links = new LinkedList();
        links.add(repositoryLink);
        links.add(pipelineLink);
        links.add(apiLink);
        links.add(swaggerLink);
        return links;
    }

    private List<HelmDependency> getDependencies(String projectId, String token) {
        String helmChartYaml = repositoryCommunication.getHelmChartYamlContentFromClient(projectId, token);
        if (!helmChartYaml.equals(Constants.EMPTY)) {
            List<HelmDependency> dependencies = getDependenciesFromHelmChartYaml(helmChartYaml);
            if (!dependencies.isEmpty()) {
                return dependencies;
            }
        }
        return Collections.emptyList();
    }

    private List<HelmDependency> getDependenciesFromHelmChartYaml(String helmChartYaml) {
        List<HelmDependency> dependencies = null;
        try {
            Yaml yaml = new Yaml();
            HelmChart helmChart = yaml.loadAs(helmChartYaml, HelmChart.class);
            dependencies = helmChart.getDependencies();
        } catch (YAMLException e) {
            e.printStackTrace();
            Logger.printError("Error while parsing a Chart.yaml");
        }
        if (dependencies == null) {
            dependencies = Collections.emptyList();
        }
        return dependencies;
    }

    private String getApiSpec(String projectId, String token) {
        String apiSpecFileContent = repositoryCommunication.getApiContentFromClient(projectId, token);
        if (!apiSpecFileContent.equals(Constants.EMPTY)) {
            return  StringUtils.chomp(apiSpecFileContent);
        } else {
            Logger.printError("No Api-File found. Return Swagger-Url");
            return getSwaggerUrl();
        }
    }

    private String getSwaggerUrl() {
        return "TODO Swagger";
    }


    private String getAddress(DeploymentMetadata deploymentMetadata) {
        return deploymentMetadata.getServiceUrl();
    }


}
