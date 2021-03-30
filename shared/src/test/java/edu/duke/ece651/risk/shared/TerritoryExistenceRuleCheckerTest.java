package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class TerritoryExistenceRuleCheckerTest {
  @Test
  public void test_territoryexist() {
    TerritoryExistenceRuleChecker rc = new TerritoryExistenceRuleChecker(null);
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
    ActionInfo a2 = af.createMoveActionInfo("Player 1", "Test", "Midkemia", unitNum1);
    ActionInfo a3 = af.createMoveActionInfo("Player 1", "Narnia", "Test", unitNum1);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action is invalid: source Territory does not exist", rc.checkMyRule(a2, worldmap));
    assertEquals("That action is invalid: destination Territory does not exist", rc.checkMyRule(a3, worldmap));
  }
}
