package edu.kit.microservice_management.logic.model.query.selectors;

public class Selector {
    private String label;
    private String value;
    private String operator;

    public Selector(String label, String value) {
        if (label == null || value == null) {
            throw new NullPointerException("The selector's label and value cannot be null!");
        }
        this.label = label;
        this.value = value;
        this.operator = "=";
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOperator() {
        return operator;
    }

    protected void setOperator(String operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        return label + operator + "\"" + value + "\"";
    }
}
