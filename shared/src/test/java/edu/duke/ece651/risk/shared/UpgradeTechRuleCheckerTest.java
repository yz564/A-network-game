package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UpgradeTechRuleCheckerTest {
  @Test
  public void test_upgradetech() {
    UpgradeTechRuleChecker rc = new UpgradeTechRuleChecker(null);
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createUpgradeTechActionInfo("Player 1", 2);
    ActionInfo a2 = af.createUpgradeTechActionInfo("Player 1", 7);
    ActionInfo a3 = af.createUpgradeTechActionInfo("Player 1", 1);
    ActionInfo a4 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level3", 5);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action is invalid: new tech level is invalid", rc.checkMyRule(a2, worldmap));
    assertEquals("That action is invalid: new tech level is invalid", rc.checkMyRule(a3, worldmap));
    assertNull(rc.checkMyRule(a4, worldmap));
  }
}
