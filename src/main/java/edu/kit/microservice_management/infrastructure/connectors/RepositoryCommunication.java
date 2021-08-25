package edu.kit.microservice_management.infrastructure.connectors;


import edu.kit.microservice_management.infrastructure.configuration.Constants;
import edu.kit.microservice_management.logic.model.metadata.DeploymentMetadata;
import edu.kit.microservice_management.logic.model.metadata.RepositoryMetadata;
import edu.kit.microservice_management.infrastructure.utils.logging.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Component
public class RepositoryCommunication {
    private WebClient webClient;
    private String gitLabApiRoot;

    public RepositoryCommunication(@Value("${gitlab.api.root}") String gitlabApiRoot) {
        this.gitLabApiRoot = gitlabApiRoot;
        DefaultUriBuilderFactory factoryWithoutEncoding = new DefaultUriBuilderFactory(gitlabApiRoot);
        factoryWithoutEncoding.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        this.webClient = WebClient.builder()
            .uriBuilderFactory(factoryWithoutEncoding)
            .baseUrl(gitlabApiRoot)
            .build();
    }

    public String getCodeOwnersContentFromClient(String projectId, String token) {
        String codeOwner = getFileFromMaster(Constants.CODEOWNERS_PATH, projectId, token);
        return codeOwner;
    }

    public String getHelmChartYamlContentFromClient(String projectId, String token) {
        String helmChartYaml = getFileFromMaster(Constants.HELM_CHART_PATH, projectId, token);
        return helmChartYaml;
    }

    public String getApiContentFromClient(String projectId, String token) {
        String apiYaml = getFileFromMaster(Constants.API_FILE_PATH, projectId, token);
        return apiYaml;
    }

    private String getFileFromMaster(String fileRelativePathInRepository, String projectId, String token) {
        String content;
        try {
            content = webClient.get().uri(uriBuilder ->
                uriBuilder.
                    path(projectId + fileRelativePathInRepository).
                    queryParam(Constants.TOKEN_FIELD_NAME_HTTP, token).
                    queryParam("ref", "master").
                    build()).
                retrieve().
                bodyToMono(String.class).block();
        } catch (WebClientException e) {
            e.printStackTrace();
            Logger.printError("Error while getting the file [" + fileRelativePathInRepository + "]");
            content = Constants.EMPTY;
        }
        return content;
    }

    public boolean makeEmptyGet() {
        try {
            webClient.get().retrieve().bodyToMono(String.class).block();
            return true;
        } catch (WebClientException e) {
            return false;
        }
    }

    public boolean isAlive() {
        return makeEmptyGet();
    }

    public String getSwaggerLink(DeploymentMetadata deploymentMetadata) {
        String serviceUrl = deploymentMetadata.getServiceUrl();
        String swaggerUrl = createSwaggerUrlFromServiceUrl(serviceUrl);
        boolean swaggerExists = checkSwaggerExists(swaggerUrl);
        if (swaggerExists) {
            return swaggerUrl;
        } else {
            return Constants.EMPTY;
        }
    }

    private boolean checkSwaggerExists(String swaggerUrl) {
        try {
            WebClient.create(swaggerUrl).get().retrieve().bodyToMono(String.class).block();
        } catch (WebClientException e) {
            return false;
        }
        return true;
    }

    private String createSwaggerUrlFromServiceUrl(String serviceUrl) {
        return serviceUrl + Constants.SWAGGER_URL_PART_DEFAULT;
    }

    public String getApiLink(RepositoryMetadata repositoryMetadata, String projectId, String token) {
        boolean apiFileExists = checkFileExists(Constants.API_FILE_PATH, projectId, token);
        if (apiFileExists) {
            String absoluteApiFileURL = getAbsoluteApiFileURL(repositoryMetadata, Constants.API_FILE_PATH);
            return absoluteApiFileURL;
        } else {
            return Constants.EMPTY;
        }
    }

    private String getAbsoluteApiFileURL(RepositoryMetadata repositoryMetadata, String apiFilePath) {
        return repositoryMetadata.getUrl() + Constants.API_IN_REPOSITORY;
    }

    private boolean checkFileExists(String fileRelativePathInRepository, String projectId, String token) {
        try {
            webClient.get().uri(uriBuilder ->
                uriBuilder.
                    path(projectId + fileRelativePathInRepository).
                    queryParam(Constants.TOKEN_FIELD_NAME_HTTP, token).
                    queryParam("ref", "master").
                    build()).
                retrieve().
                bodyToMono(String.class).block();
        } catch (WebClientException e) {
            return false;
        }
        return true;
    }
}
