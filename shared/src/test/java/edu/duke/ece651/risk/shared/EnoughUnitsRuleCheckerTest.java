package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class EnoughUnitsRuleCheckerTest {
  @Test
  public void test_enoughunits() {
    EnoughUnitsRuleChecker rc = new EnoughUnitsRuleChecker(null);
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
    ActionInfo a2 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level1", 15);
    ActionInfo a3 = af.createUpgradeTechActionInfo("Player 1", 2);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action is invalid: source Territory does not contain enough level0 units",
        rc.checkMyRule(a2, worldmap));
    assertNull(rc.checkMyRule(a3, worldmap));
  }
}
