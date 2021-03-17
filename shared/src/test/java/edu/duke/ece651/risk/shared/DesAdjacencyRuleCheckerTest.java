package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DesAdjacencyRuleCheckerTest {
  @Test
  public void test_desadjacency() {
    DesAdjacencyRuleChecker rc = new DesAdjacencyRuleChecker(null);
    WorldMapFactory factory = new V1MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    ActionInfo a1 = new ActionInfo("Player 1", "Narnia", "Elantris", 3);
    ActionInfo a2 = new ActionInfo("Player 1", "Narnia", "Oz", 3);
    assertNull(rc.checkMyRule(a1, worldmap));
    assertEquals("That action is invalid: destination Territory is not adjacent to source Territory",
        rc.checkMyRule(a2, worldmap));
  }
}
