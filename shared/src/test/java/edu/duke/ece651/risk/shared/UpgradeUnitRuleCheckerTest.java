package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UpgradeUnitRuleCheckerTest {
  @Test
  public void test_upgradeunit() {
    UpgradeUnitRuleChecker rc = new UpgradeUnitRuleChecker(null);
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    t1.tryAddTroopUnits("level3", 10);
    ActionInfoFactory af = new ActionInfoFactory();
    p1.setTechLevel(3);
    ActionInfo a1 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level3", 5);
    ActionInfo a2 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level3", "level4", 5);
    ActionInfo a3 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level3", "level2", 5);
    ActionInfo a4 = af.createUpgradeTechActionInfo("Player 1", 2);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action is invalid: new troop level is invalid", rc.checkMyRule(a2, worldmap));
    assertEquals("That action is invalid: new troop level is less than current troop level",
        rc.checkMyRule(a3, worldmap));
    assertNull(rc.checkMyRule(a4, worldmap));
  }
}
