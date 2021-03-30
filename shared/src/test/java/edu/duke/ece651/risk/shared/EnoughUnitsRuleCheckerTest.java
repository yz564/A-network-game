package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class EnoughUnitsRuleCheckerTest {
  @Test
  public void test_srcvalidity() {
    EnoughUnitsRuleChecker rc = new EnoughUnitsRuleChecker(null);
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    Territory t1 = worldmap.getTerritory("Narnia");
    Territory t2 = worldmap.getTerritory("Midkemia");
    t1.tryAddTroopUnits("level0", 10);
    t2.tryAddTroopUnits("level0", 12);
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfoFactory af = new ActionInfoFactory();
    HashMap<String, Integer> unitNum2 = new HashMap<>();
    unitNum2.put("level0", 11);
    ActionInfo a3 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum2);
    assertEquals("That action is invalid: source Territory does not contain enough level0 units",
        rc.checkMyRule(a3, worldmap));
  }
}
