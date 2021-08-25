package edu.kit.microservice_management.logic.model.query;

import edu.kit.microservice_management.logic.model.query.selectors.Selector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class PrometheusQuery {

    private String metric;
    private Collection<Selector> selectors;
    private String predicate;

    public PrometheusQuery(String metric, Collection<Selector> selectors, String predicate) {
        if (metric == null) {
            throw new NullPointerException("You must provide a metric!");
        }

        this.metric = metric;
        this.selectors = selectors;
        this.predicate = predicate;
    }

    public PrometheusQuery(String metric, Collection<Selector> selectors) {
        if (metric == null) {
            throw new NullPointerException("You must provide a metric!");
        }

        this.metric = metric;
        this.selectors = selectors;
        this.predicate = null;
    }

    public PrometheusQuery(String metric) {
        if (metric == null) {
            throw new NullPointerException("You must provide a metric!");
        }

        this.metric = metric;
        this.selectors = null;
        this.predicate = null;
    }

    public PrometheusQuery(String metric, Selector selector, String predicate) {
        if (metric == null) {
            throw new NullPointerException("You must provide a metric!");
        }

        this.metric = metric;
        if (selector != null) {
            this.selectors = new ArrayList<>(){{
                add(selector);
            }};
        }

        this.predicate = predicate;
    }

    public PrometheusQuery(String metric, Selector selector) {
        if (metric == null || selector == null) {
            throw new NullPointerException("You must provide a metric!");
        }

        this.metric = metric;
        if (selector != null) {
            this.selectors = new ArrayList<>(){{
                add(selector);
            }};
        }
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(metric);

        if (selectors != null && !selectors.isEmpty()){
            sb.append("{");
            for (Selector selector : selectors) {
                if (selector != null) {
                    sb.append(selector.toString()).append(", ");
                }
            }
            // replace extra comma and space with }
            sb.replace(sb.length()-2, sb.length(), "} ");
        }

        if (predicate != null && !predicate.isEmpty()) {
            sb.append(predicate);
        }

        return sb.toString().trim();
    }

}
