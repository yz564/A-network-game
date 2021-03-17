package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SrcValidityRuleCheckerTest {
  @Test
  public void test_srcvalidity() {
    SrcValidityRuleChecker rc = new SrcValidityRuleChecker(null);
    WorldMapFactory factory = new V1MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    Territory t1 = worldmap.getTerritory("Narnia");
    Territory t2 = worldmap.getTerritory("Midkemia");
    t1.tryAddUnits(10);
    t2.tryAddUnits(12);
    ActionInfo a1 = new ActionInfo("Player 1", "Narnia", "Midkemia", 3);
    ActionInfo a2 = new ActionInfo("Player 1", "Elantris", "Midkemia", 3);
    ActionInfo a3 = new ActionInfo("Player 1", "Narnia", "Midkemia", 11);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action is invalid: source Territory belong to a different player", rc.checkMyRule(a2, worldmap));
    assertEquals("That action is invalid: source Territory does not contain enough units",
        rc.checkMyRule(a3, worldmap));
  }
}
