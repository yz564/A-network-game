package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ActionInfoTest {
  @Test
  public void test_default_constructor() {
    ActionInfo info = new ActionInfo("AAA", "move");
    assertEquals("AAA", info.getSrcOwnerName());
    assertEquals("move", info.getActionType());
  }

  @Test
  public void test_territory_action_constructor() {
    HashMap<String, Integer> unitNum = new HashMap<String, Integer>();
    unitNum.put("level0", 3);
    TerritoryActionInfo infoDetail = new TerritoryActionInfo("a", "b", unitNum);
    ActionInfo info = new ActionInfo("Player 1", "move", infoDetail);
    assertEquals("a", info.getTerritoryActionInfo().getSrcName());

    assertNull(info.getUpgradeTechActionInfo());
    assertNull(info.getUpgradeUnitActionInfo());
  }

  @Test
  public void test_upgrade_unit_action_constructor() {
    UpgradeUnitActionInfo infoDetail = new UpgradeUnitActionInfo("1", "level3", "level5", 10);
    ActionInfo info = new ActionInfo("Player 1", "upgrade unit", infoDetail);
    assertEquals(10, info.getUpgradeUnitActionInfo().getNumToUpgrade());
  }

  @Test
  public void test_upgrade_tech_action_constructor() {
    UpgradeTechActionInfo infoDetail = new UpgradeTechActionInfo(3);
    ActionInfo info = new ActionInfo("Player 1", "upgrade tech", infoDetail);
    assertEquals(3, info.getUpgradeTechActionInfo().getNewTechLevel());
  }

  @Test
  public void test_getsrcdesname() {
    HashMap<String, Integer> unitNum = new HashMap<String, Integer>();
    unitNum.put("level0", 3);
    TerritoryActionInfo infoDetail = new TerritoryActionInfo("Narnia", "Elantris", unitNum);
    ActionInfo info = new ActionInfo("Player 1", "attack", infoDetail);
    assertEquals("Narnia", info.getSrcName());
    assertEquals("Elantris", info.getDesName());

    UpgradeUnitActionInfo infoDetail2 = new UpgradeUnitActionInfo("Oz", "level3", "level5", 10);
    ActionInfo info2 = new ActionInfo("Player 1", "upgrade unit", infoDetail2);
    assertEquals("Oz", info2.getSrcName());
  }

  @Test
  public void test_getnumunits() {
    HashMap<String, Integer> unitNum = new HashMap<String, Integer>();
    unitNum.put("level0", 3);
    TerritoryActionInfo infoDetail = new TerritoryActionInfo("Narnia", "Elantris", unitNum);
    ActionInfo info = new ActionInfo("Player 1", "attack", infoDetail);
    assertEquals(unitNum, info.getNumUnits());
    UpgradeUnitActionInfo infoDetail2 = new UpgradeUnitActionInfo("Oz", "level0", "level5", 3);
    ActionInfo info2 = new ActionInfo("Player 1", "upgrade unit", infoDetail2);
    unitNum.put("level5", 0);
    assertEquals(unitNum, info2.getNumUnits());
    UpgradeTechActionInfo infoDetail3 = new UpgradeTechActionInfo(3);
    ActionInfo info3 = new ActionInfo("Player 1", "upgrade tech", infoDetail3);
    assertNull(info3.getNumUnits());
  }

  @Test
  public void test_getlevel() {
    HashMap<String, Integer> unitNum = new HashMap<String, Integer>();
    unitNum.put("level0", 3);
    TerritoryActionInfo infoDetail = new TerritoryActionInfo("Narnia", "Elantris", unitNum);
    ActionInfo info = new ActionInfo("Player 1", "attack", infoDetail);
    assertNull(info.getOldUnitLevel());
    assertNull(info.getNewUnitLevel());
    assertEquals(0, info.getNewTechLevel());
  }

  @Test
  public void test_gettotalunits() {
    HashMap<String, Integer> unitNum = new HashMap<String, Integer>();
    unitNum.put("level0", 3);
    TerritoryActionInfo infoDetail = new TerritoryActionInfo("Narnia", "Elantris", unitNum);
    ActionInfo info = new ActionInfo("Player 1", "attack", infoDetail);
    UpgradeTechActionInfo infoDetail2 = new UpgradeTechActionInfo(3);
    ActionInfo info2 = new ActionInfo("Player 1", "upgrade unit", infoDetail2);
    assertEquals(3, info.getTotalNumUnits());
    assertEquals(0, info2.getTotalNumUnits());
  }
}
