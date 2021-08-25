package edu.kit.microservice_management.logic.model.query.selectors;

public class InequalitySelector extends Selector {
    public InequalitySelector(String label, String value) {
        super(label, value);
        this.setOperator("!=");
    }
}
