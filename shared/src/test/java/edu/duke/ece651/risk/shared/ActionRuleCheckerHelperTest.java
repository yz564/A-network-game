package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class ActionRuleCheckerHelperTest {
  @Test
  public void test_actionrulecheckerhelper() {
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 5);
    ActionInfoFactory af = new ActionInfoFactory();
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfo a1 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
    ActionInfo a2 = af.createAttackActionInfo("Player 1", "Narnia", "Elantris", unitNum1);
    ActionInfo a3 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level1", 2);
    ActionInfo a4 = af.createUpgradeTechActionInfo("Player 1", 2);
    ActionRuleCheckerHelper rc = new ActionRuleCheckerHelper();
    assertNull(rc.checkRuleForMove(a1, worldmap));
    assertNull(rc.checkRuleForAttack(a2, worldmap));
    assertNull(rc.checkRuleForUpgradeUnit(a3, worldmap));
    assertNull(rc.checkRuleForUpgradeTech(a4, worldmap)); 
  }
}
