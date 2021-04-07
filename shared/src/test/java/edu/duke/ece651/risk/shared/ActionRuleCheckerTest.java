package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class ActionRuleCheckerTest {
  @Test
  public void test_checkaction() {
    ActionRuleChecker rc1 = new TerritoryExistenceRuleChecker(new SrcOwnershipRuleChecker(
        new TroopExistenceRuleChecker(new EnoughUnitsRuleChecker(new DesReachableRuleChecker(null)))));
    ActionRuleChecker rc2 = new TerritoryExistenceRuleChecker(new SrcOwnershipRuleChecker(new TroopExistenceRuleChecker(
        new EnoughUnitsRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))))));
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 5);
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createMoveActionInfo("Player 1", "Narnia", "Oz", unitNum1);
    ActionInfo a2 = af.createAttackActionInfo("Player 1", "Narnia", "Elantris", unitNum1);
    ActionInfo a3 = af.createMoveActionInfo("Player 1", "Narnia", "Hogwarts", unitNum1);
    assertNull(rc1.checkAction(a1, worldmap));
    assertNull(rc2.checkAction(a2, worldmap));
    assertEquals("That action is invalid: destination Territory is not reachable from source Territory",
        rc1.checkAction(a3, worldmap));
  }
}
