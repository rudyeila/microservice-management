package edu.kit.microservice_management.logic.model.query;

import edu.kit.microservice_management.logic.model.query.selectors.RegexSelector;
import edu.kit.microservice_management.logic.model.query.selectors.Selector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class PrometheusQueryTest {

    @Test
    void testBuildingQueryWithMetricOnly() {
        String metric = "kube_pod_state_phase";

        PrometheusQuery query = new PrometheusQuery(metric);

        String expected = "kube_pod_state_phase";
        assertEquals(expected, query.toString());
    }

    @Test
    void testBuildingQueryWithSingleSelectorNoPredicate() {
        String metric = "kube_pod_state_phase";
        Collection<Selector> selectors = new ArrayList<>() {{
            add(new Selector("namespace", "cm"));
        }};

        PrometheusQuery query = new PrometheusQuery(metric, selectors);

        String expected = "kube_pod_state_phase{namespace=\"cm\"}";
        assertEquals(expected, query.toString());
    }

    @Test
    void testBuildingQueryWithSingleSelectorAndPredicate() {
        String metric = "kube_pod_state_phase";
        Collection<Selector> selectors = new ArrayList<>() {{
            add(new Selector("namespace", "cm"));
        }};
        String predicate = "> 0";

        PrometheusQuery query = new PrometheusQuery(metric, selectors, predicate);

        String expected = "kube_pod_state_phase{namespace=\"cm\"} > 0";
        assertEquals(expected, query.toString());
    }

    @Test
    void testBuildingQueryWithSelectorsAndNoPredicate() {
        String metric = "kube_pod_state_phase";
        Collection<Selector> selectors = new ArrayList<>() {{
            add(new Selector("namespace", "cm"));
            add(new RegexSelector("pod", "cm-vehicle-*"));
        }};

        PrometheusQuery query = new PrometheusQuery(metric, selectors);

        String expected = "kube_pod_state_phase{namespace=\"cm\", pod=~\"cm-vehicle-*\"}";
        assertEquals(expected, query.toString());
    }

    @Test
    void testBuildingQueryWithSelectorsAndPredicate() {
        String metric = "kube_pod_state_phase";
        Collection<Selector> selectors = new ArrayList<>() {{
            add(new Selector("namespace", "cm"));
            add(new RegexSelector("pod", "cm-vehicle-*"));
        }};
        String predicate = "> 0";

        PrometheusQuery query = new PrometheusQuery(metric, selectors, predicate);

        String expected = "kube_pod_state_phase{namespace=\"cm\", pod=~\"cm-vehicle-*\"} > 0";
        assertEquals(expected, query.toString());
    }

    @Test
    void testConstructorsThrowsNullPointerException() {
        assertThrows(NullPointerException.class,
                ()->{
                    new PrometheusQuery(null);
                });
    }

}