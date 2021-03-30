package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeUnitActionInfoTest {

    @Test
    void test_getters() {
        UpgradeUnitActionInfo info = new UpgradeUnitActionInfo("ABC", 3, 5, 10);
        assertEquals("ABC", info.getSrcName());
        assertEquals(3, info.getOldUnitLevel());
        assertEquals(5, info.getNewUnitLevel());
        assertEquals(10, info.getNumToUpgrade());
    }

    @Test
    void test_setters() {
        UpgradeUnitActionInfo info = new UpgradeUnitActionInfo();
        assertNull(info.getSrcName());
        assertNull(info.getOldUnitLevel());
        assertNull(info.getNewUnitLevel());
        assertNull(info.getNumToUpgrade());
        info.setSrcName("ABC");
        info.setOldUnitLevel(3);
        info.setNewUnitLevel(5);
        info.setNumToUpgrade(10);
        assertEquals("ABC", info.getSrcName());
        assertEquals(3, info.getOldUnitLevel());
        assertEquals(5, info.getNewUnitLevel());
        assertEquals(10, info.getNumToUpgrade());
    }
}
