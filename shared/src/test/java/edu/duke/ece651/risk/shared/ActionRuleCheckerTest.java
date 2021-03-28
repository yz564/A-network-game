package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class ActionRuleCheckerTest {
  @Test
  public void test_checkaction() {
    ActionRuleChecker rc1 = new TerritoryExistenceRuleChecker(
        new SrcValidityRuleChecker(new DesReachableRuleChecker(null)));
    ActionRuleChecker rc2 = new TerritoryExistenceRuleChecker(
        new SrcValidityRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))));
    WorldMapFactory factory = new V1MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("Basic", 5);
    ActionInfo a1 = new ActionInfo("Player 1", "Narnia", "Oz", 3);
    ActionInfo a2 = new ActionInfo("Player 1", "Narnia", "Elantris", 3);
    ActionInfo a3 = new ActionInfo("Player 1", "Narnia", "Hogwarts", 3);
    assertNull(rc1.checkAction(a1, worldmap));
    assertNull(rc2.checkAction(a2, worldmap));
    assertEquals("That action is invalid: destination Territory is not reachable from source Territory",
        rc1.checkAction(a3, worldmap));
  }
}





