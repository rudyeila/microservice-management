package edu.kit.microservice_management.logic.model;

import edu.kit.microservice_management.logic.model.helm.HelmDependency;
import edu.kit.microservice_management.logic.model.health.Health;

import java.beans.ConstructorProperties;
import java.util.List;

public class Microservice {

    private String hostname;
    private String version;
    private String address;
    private String codeOwner;
    private List<HelmDependency> dependencies;
    private List<Link> links;
    private Health health;

    @ConstructorProperties({"hostname", "version", "address", "codeOwner", "dependencies", "links", "health"})
    public Microservice(String hostname, String version, String address, String codeOwner, List<HelmDependency> dependencies, List<Link> links, Health health) {
        this.hostname = hostname;
        this.version = version;
        this.address = address;
        this.codeOwner = codeOwner;
        this.dependencies = dependencies;
        this.links = links;
        this.health = health;
    }



    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCodeOwner() {
        return codeOwner;
    }

    public void setCodeOwner(String codeOwner) {
        this.codeOwner = codeOwner;
    }

    public List<HelmDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<HelmDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public Health getHealth() { return health; }

    public void setHealth(Health health) { this.health = health; }

    }
