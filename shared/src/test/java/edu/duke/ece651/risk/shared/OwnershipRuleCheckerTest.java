package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class OwnershipRuleCheckerTest {
  @Test
  public void test_ownership() {
    OwnershipRuleChecker rc = new OwnershipRuleChecker(null);
    WorldMapFactory factory = new V1MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("Basic", 3);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createAttackActionInfo("Player 1", "Narnia", "Elantris", unitNum1);
    ActionInfo a2 = af.createAttackActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
    ActionInfo a3 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
    ActionInfo a4 = af.createMoveActionInfo("Player 1", "Elantris", "Narnia", unitNum1);
    ActionInfo a5 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "Basic", "Basic", 1);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action in invalid: destination Territory does not belong to a different player",
        rc.checkMyRule(a2, worldmap));
    assertNull(rc.checkMyRule(a3, worldmap));
    assertEquals("That action is invalid: source Territory belong to a different player", rc.checkMyRule(a4, worldmap));
    assertNull(rc.checkMyRule(a5, worldmap));
  }
}
