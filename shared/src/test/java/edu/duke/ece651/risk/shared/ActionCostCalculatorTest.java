package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class ActionCostCalculatorTest {
  private WorldMap makeTestMap() {
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    return worldmap;
  }

  @Test
  public void test_calculator() {
    ActionCostCalculator cal = new ActionCostCalculator();
    WorldMap worldmap = makeTestMap();
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
    ActionInfo a2 = af.createAttackActionInfo("Player 1", "Elantris", "Midkemia", unitNum1);
    ActionInfo a3 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level1", 5);
    ActionInfo a4 = af.createUpgradeTechActionInfo("Player 1", 2);
    HashMap<String, Integer> costs = new HashMap<String, Integer>();
    costs.put("food", 12);
    assertEquals(costs, cal.calculateMoveCost(a1, worldmap));
    costs.put("food", 3);
    assertEquals(costs, cal.calculateAttackCost(a2, worldmap));
    HashMap<String, Integer> costs2 = new HashMap<String, Integer>();
    costs2.put("tech", 15);
    assertEquals(costs2, cal.calculateUpgradeUnitCost(a3, worldmap));
    costs2.put("tech", 50);
    assertEquals(costs2, cal.calculateUpgradeTechCost(a4, worldmap));

    assertNull(cal.calculateMoveCost(a3, worldmap));
    assertNull(cal.calculateAttackCost(a3, worldmap));
    assertNull(cal.calculateUpgradeUnitCost(a1, worldmap));
    assertNull(cal.calculateUpgradeTechCost(a2, worldmap));
  }
}
