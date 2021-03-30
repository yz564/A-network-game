package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeTechActionInfoTest {
    @Test
    public void test_getters() {
        UpgradeTechActionInfo info = new UpgradeTechActionInfo(3);
        assertEquals(3, info.getNewTechLevel());
    }

    @Test
    public void test_setters() {
        UpgradeTechActionInfo info = new UpgradeTechActionInfo();
        assertNull(info.getNewTechLevel());
        info.setNewTechLevel(3);
        assertEquals(3, info.getNewTechLevel());
    }
}
