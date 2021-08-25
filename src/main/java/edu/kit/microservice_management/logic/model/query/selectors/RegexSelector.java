package edu.kit.microservice_management.logic.model.query.selectors;

public class RegexSelector extends Selector {
    public RegexSelector(String label, String value) {
        super(label, value);
        this.setOperator("=~");
    }
}
