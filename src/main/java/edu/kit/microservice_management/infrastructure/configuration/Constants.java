package edu.kit.microservice_management.infrastructure.configuration;

public class Constants {
    //File paths
    public static final String CODEOWNERS_PATH = "/repository/files/CODEOWNERS/raw";
    public static final String HELM_CHART_PATH = "/repository/files/helm%2FChart.yaml/raw";
    public static final String API_FILE_PATH = "/repository/files/pages%2Fapi.md/raw";
    public static final String API_IN_REPOSITORY = "/-/blob/master/pages%2Fapi.md";

    //Messages
    public static final String REGISTER_POST_RESPONSE_SUCCESS = "MicroserviceManagement: Microservice persisted";
    public static final String REGISTER_POST_RESPONSE_ERROR_WHILE_SENDING_MICROSERVICE_TO_SERVICE = "MicroserviceManagement: send Microservice to Domain Service: Error while sending";
    public static final String REGISTER_POST_RESPONSE_FAIL_SERVICE_OFFLINE = "MicroserviceManagement: send Microservice to Domain Service: Error: Domain Service is offline";
    public static final String CHECK_MICROSERVICE_IN_DATABASE_ERROR = "Error while checking microservice";
    public static final String DEFAULT_HTTP_GET_ANSWER = "Hello from MicroserviceManagement";
    public static final String UNREGISTER_POST_RESPONSE_SUCCESS = "MicroserviceManagement: Microservice unregistered";
    public static final String UNREGISTER_POST_RESPONSE_FAIL = "MicroserviceManagement: unregister Microservice: Error";
    public static final String REGISTER_POST_RESPONSE_FALSE_INPUT_STRUCTURE = "MicroserviceManagement: Error: False input format";
    public static final String REGISTER_POST_RESPONSE_FAIL_GITLAB_OFFLINE = "MicroserviceManagement: send Microservice to Domain Service: Error: GitLab is offline";

    //Field Names
    public static final String TOKEN_FIELD_NAME_HTTP = "private_token";

    //Constant Strings
    public static final String REPOSITORY_LINK_NAME = "repository";
    public static final String PIPELINE_LINK_NAME = "pipeline";
    public static final String API_LINK_NAME = "api";
    public static final String SWAGGER_LINK_NAME = "swagger";

    public static final String EMPTY = "";
    public static final String SWAGGER_URL_PART_DEFAULT = "/swagger-ui";
}
