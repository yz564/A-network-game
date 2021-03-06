package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DesOwnershipRuleCheckerTest {
  @Test
  public void test_desownership() {
    DesOwnershipRuleChecker rc = new DesOwnershipRuleChecker(null);
    WorldMapFactory factory = new V1MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    ActionInfo a1 = new ActionInfo("Player 1", "Narnia", "Elantris", 3);
    ActionInfo a2 = new ActionInfo("Player 1", "Narnia", "Midkemia", 3);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action in invalid: destination Territory does not belong to a different player",
        rc.checkMyRule(a2, worldmap));
  }
}
