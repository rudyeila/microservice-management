package edu.kit.microservice_management.logic.model.query.selectors;

public class EqualitySelector extends Selector {
    public EqualitySelector(String label, String value) {
        super(label, value);
        this.setOperator("=");
    }
}
