package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UpgradeLevelValidityRuleCheckerTest {
  @Test
  public void test_upgradelevel() {
    UpgradeLevelValidityRuleChecker rc = new UpgradeLevelValidityRuleChecker(null);
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    t1.tryAddTroopUnits("level6", 10);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level1", 5);
    ActionInfo a2 = af.createUpgradeTechActionInfo("Player 1", 2);
    ActionInfo a3 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level5", 5);
    ActionInfo a4 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level6", "level7", 5);
    ActionInfo a5 = af.createUpgradeTechActionInfo("Player 1", 7);
    ActionInfo a6 = af.createUpgradeTechActionInfo("Player 1", 0);
    ActionInfo a7 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level6", "level6", 5);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertNull(rc.checkMyRule(a2, worldmap));
    assertEquals("That action is invalid: upgrade level is more than maximum technology level allowed",
        rc.checkMyRule(a3, worldmap));
    assertEquals("That action is invalid: upgrade level is invalid", rc.checkMyRule(a4, worldmap));
    assertEquals("That action is invalid: upgrade level is invalid", rc.checkMyRule(a5, worldmap));
    assertEquals("That action is invalid: upgrade level is not more than current level", rc.checkMyRule(a6, worldmap));
    p1.setTechLevel(6);
    assertEquals("That action is invalid: current level is at maximum", rc.checkMyRule(a7, worldmap));
  }
}
