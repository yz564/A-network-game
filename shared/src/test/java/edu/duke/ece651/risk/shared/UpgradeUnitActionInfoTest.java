package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UpgradeUnitActionInfoTest {

  @Test
  void test_getters() {
    UpgradeUnitActionInfo info = new UpgradeUnitActionInfo("ABC", "level3", "level5", 10);
    assertEquals("ABC", info.getSrcName());
    assertEquals("level3", info.getOldUnitLevel());
    assertEquals("level5", info.getNewUnitLevel());
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
    info.setOldUnitLevel("level3");
    info.setNewUnitLevel("level5");
    info.setNumToUpgrade(10);
    assertEquals("ABC", info.getSrcName());
    assertEquals("level3", info.getOldUnitLevel());
    assertEquals("level5", info.getNewUnitLevel());
    assertEquals(10, info.getNumToUpgrade());
  }
}
