package edu.kit.microservice_management.logic.model.query.selectors;
import edu.kit.microservice_management.logic.model.query.PrometheusQuery;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SelectorsTest {

    @Test
    void testSelector() {
        Selector selector = new Selector("label", "value");

        String expected = "label=\"value\"";

        assertEquals(expected, selector.toString());
    }

    @Test
    void testEqualitySelector() {
        Selector selector = new EqualitySelector("label", "value");

        String expected = "label=\"value\"";

        assertEquals(expected, selector.toString());
    }

    @Test
    void testINEqualitySelector() {
        Selector selector = new InequalitySelector("label", "value");

        String expected = "label!=\"value\"";

        assertEquals(expected, selector.toString());
    }

    @Test
    void testRegexSelector() {
        Selector selector = new RegexSelector("label", "value");

        String expected = "label=~\"value\"";

        assertEquals(expected, selector.toString());
    }

    @Test
    void testNegRegexSelector() {
        Selector selector = new NegRegexSelector("label", "value");

        String expected = "label!~\"value\"";

        assertEquals(expected, selector.toString());
    }

    @Test
    void testConstructorsThrowNLP() {
        assertThrows(NullPointerException.class,
                ()->{
                    new Selector(null, null);
                });
        assertThrows(NullPointerException.class,
                ()->{
                    new EqualitySelector(null, null);
                });
        assertThrows(NullPointerException.class,
                ()->{
                    new InequalitySelector(null, null);
                });
        assertThrows(NullPointerException.class,
                ()->{
                    new RegexSelector(null, null);
                });
        assertThrows(NullPointerException.class,
                ()->{
                    new NegRegexSelector(null, null);
                });
    }

}
