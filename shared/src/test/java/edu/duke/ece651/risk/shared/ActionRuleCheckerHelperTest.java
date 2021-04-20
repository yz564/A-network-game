package edu.duke.ece651.risk.shared;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ActionRuleCheckerHelperTest {
  @Test
  public void test_actionrulecheckerhelper() {
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
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
    assertEquals(
        "That action is invalid: you do not have enough resources to perform the action.",
        rc.checkRuleForUpgradeTech(a4, worldmap));
  }

  @Test
  public void test_attackchecker() {
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    PlayerInfo p2 = new PlayerInfo("Player 1", 30, 30);
    PlayerInfo p3 = new PlayerInfo("Player 1", 30, 30);
    worldmap.tryAddPlayerInfo(p1);
    worldmap.tryAddPlayerInfo(p2);
    worldmap.tryAddPlayerInfo(p3);
    Territory t1 = worldmap.getTerritory("Narnia");
    Territory t2 = worldmap.getTerritory("Scadrial");
    t1.tryAddTroopUnits("level0", 5);
    ActionInfoFactory af = new ActionInfoFactory();
    HashMap<String, Integer> unitNum1 = new HashMap<>();
    unitNum1.put("level0", 3);
    ActionInfo a2 = af.createAttackActionInfo("Player 1", "Narnia", "Scadrial", unitNum1);
    ActionRuleChecker rc =
        new TerritoryExistenceRuleChecker(
            new SrcOwnershipRuleChecker(
                new TroopExistenceRuleChecker(
                    new EnoughUnitsRuleChecker(
                        new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))))));
    assertEquals(
        "That action is invalid: destination Territory is not adjacent to source Territory",
        rc.checkAction(a2, worldmap));
  }

  @Test
  public void test_upgradeunitchecker() {
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    PlayerInfo p1 = new PlayerInfo("Player 1", 1000, 1000);
    PlayerInfo p2 = new PlayerInfo("Player 2", 1000, 1000);
    PlayerInfo p3 = new PlayerInfo("Player 3", 1000, 1000);
    worldmap.tryAddPlayerInfo(p1);
    worldmap.tryAddPlayerInfo(p2);
    worldmap.tryAddPlayerInfo(p3);
    Territory t1 = worldmap.getTerritory("Narnia");
    Territory t2 = worldmap.getTerritory("Scadrial");
    t1.tryAddTroopUnits("level0", 10);
    ActionRuleCheckerHelper rc = new ActionRuleCheckerHelper();
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createUpgradeUnitActionInfo("Player 1", "Narnia", "level0", "level4", 5);
    assertEquals(
        "That action is invalid: new troop level is invalid",
        rc.checkRuleForUpgradeUnit(a1, worldmap));
  }

  @Test
  public void test_actionrulecheckerhelper_spy_cloaking() {
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    PlayerInfo p1 = new PlayerInfo("Player 1", 100, 100);
    p1.setTechLevel(3);
    worldmap.tryAddPlayerInfo(p1);
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    t1.tryAddSpyTroopUnits("Player 1", 1);
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo a1 = af.createUpgradeSpyUnitActionInfo("Player 1", "Narnia", 1);
    ActionInfo a2 = af.createMoveSpyActionInfo("Player 1", "Narnia", "Elantris", 1);
    ActionInfo a3 = af.createResearchCloakingActionInfo("Player 1");
    ActionInfo a4 = af.createCloakingActionInfo("Player 1", "Narnia");
    ActionRuleCheckerHelper rc = new ActionRuleCheckerHelper();
    assertNull(rc.checkRuleForUpgradeSpy(a1, worldmap));
    assertNull(rc.checkRuleForMoveSpy(a2, worldmap));
    assertNull(rc.checkRuleForResearchCloaking(a3, worldmap));
    p1.setTechLevel(3);
    p1.setIsCloakingResearched(true);
    assertNull(rc.checkRuleForCloaking(a4, worldmap));
  }

  @Test
  public void test_actionrulecheckerhelper_patent() {
    WorldMapFactory factory = new V2MapFactory();
    WorldMap worldmap = factory.makeTestWorldMap();
    worldmap.tryAssignInitOwner(1, "Player 1");
    worldmap.tryAssignInitOwner(2, "Player 2");
    worldmap.tryAssignInitOwner(3, "Player 3");
    PlayerInfo p1 = new PlayerInfo("Player 1", 100, 100);
    p1.setTechLevel(3);
    worldmap.tryAddPlayerInfo(p1);
    Territory t1 = worldmap.getTerritory("Narnia");
    t1.tryAddTroopUnits("level0", 10);
    t1.tryAddSpyTroopUnits("Player 1", 1);
    ActionInfoFactory af = new ActionInfoFactory();
    ArrayList<String> territories = new ArrayList<>();
    territories.add("Narnia");
    ActionInfo a1 = af.createResearchPatentActionInfo("Player 1", territories);
    ActionRuleCheckerHelper rc = new ActionRuleCheckerHelper();
    assertNull(rc.checkRuleForResearchPatent(a1, worldmap));
    }
}
