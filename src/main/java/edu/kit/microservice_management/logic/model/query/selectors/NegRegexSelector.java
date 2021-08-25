package edu.kit.microservice_management.logic.model.query.selectors;

public class NegRegexSelector extends Selector {

    public NegRegexSelector(String label, String value) {
        super(label, value);
        this.setOperator("!~");
    }

}
