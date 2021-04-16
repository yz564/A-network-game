package edu.duke.ece651.risk.client.view;

import edu.duke.ece651.risk.client.view.StyleMapping;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StyleMappingTest {
    @Test
    public void test_style_mapping() {
        StyleMapping sm = new StyleMapping();
        assertEquals("Pink", sm.territoryGroupColor(1));
        assertEquals("Fuqua", sm.getTerritoryName("label0"));
    }
}
