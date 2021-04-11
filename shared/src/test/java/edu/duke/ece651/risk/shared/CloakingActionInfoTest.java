package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CloakingActionInfoTest {
    @Test
    public void test_setters() {
        CloakingActionInfo info = new CloakingActionInfo();
        assertNull(info.getTargetTerritoryName());
        info.setTargetTerritoryName("Pratt");
        assertEquals("Pratt", info.getTargetTerritoryName());
    }

    @Test
    public void test_getters() {
        CloakingActionInfo info = new CloakingActionInfo("Pratt");
        assertEquals("Pratt", info.getTargetTerritoryName());
    }
}
