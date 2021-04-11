package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class EnoughResourceRuleCheckerTest {
  @Test
  public void test_enoughresources() {
    EnoughResourceRuleChecker rc = new EnoughResourceRuleChecker(null);
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
    ActionInfo a2 = af.createAttackActionInfo("Player 1", "Narnia", "Elantris", unitNum1);
    ActionInfo a3 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level1", 15);
    ActionInfo a4 = af.createUpgradeTechActionInfo("Player 1", 2);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertNull(rc.checkMyRule(a2, worldmap));
    assertEquals(
        "That action is invalid: you do not have enough resources to perform the action.",
        rc.checkMyRule(a3, worldmap));
    assertEquals(
        "That action is invalid: you do not have enough resources to perform the action.",
        rc.checkMyRule(a4, worldmap));
  }
}
