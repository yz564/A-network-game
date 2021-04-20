package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ActionInfoFactoryTest {
    @Test
    public void test_create_attack() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createAttackActionInfo("Player 1", "A", "B", new HashMap<>());
        assertEquals("A", info1.getTerritoryActionInfo().getSrcName());
        assertEquals("B", info1.getTerritoryActionInfo().getDesName());
        assertEquals(0, info1.getTerritoryActionInfo().getUnitNum().size());
        assertEquals("attack", info1.getActionType());
        assertEquals("Player 1", info1.getSrcOwnerName());
    }

    @Test
    public void test_create_move() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createMoveActionInfo("Player 1", "A", "B", new HashMap<>());
        assertEquals("A", info1.getTerritoryActionInfo().getSrcName());
        assertEquals("B", info1.getTerritoryActionInfo().getDesName());
        assertEquals(0, info1.getTerritoryActionInfo().getUnitNum().size());
        assertEquals("move", info1.getActionType());
        assertEquals("Player 1", info1.getSrcOwnerName());
    }

    @Test
    public void test_create_spy_move() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createMoveSpyActionInfo("Player 1", "A", "B", 1);
        assertEquals("A", info1.getTerritoryActionInfo().getSrcName());
        assertEquals("B", info1.getTerritoryActionInfo().getDesName());
        assertEquals(1, info1.getTerritoryActionInfo().getSpyUnitNum());
        assertEquals("move spy", info1.getActionType());
        assertEquals("Player 1", info1.getSrcOwnerName());
    }

    @Test
    public void test_create_upgrade_tech() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createUpgradeTechActionInfo("Player 1", 5);
        assertEquals(5, info1.getUpgradeTechActionInfo().getNewTechLevel());
        assertEquals("Player 1", info1.getSrcOwnerName());
        assertEquals("upgrade tech", info1.getActionType());
    }

    @Test
    public void test_create_upgrade_unit() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createUpgradeUnitActionInfo("Player 1", "A", "level2", "level4", 10);
        assertEquals("Player 1", info1.getSrcOwnerName());
        assertEquals("upgrade unit", info1.getActionType());
        assertEquals("A", info1.getUpgradeUnitActionInfo().getSrcName());
        assertEquals("level2", info1.getUpgradeUnitActionInfo().getOldUnitLevel());
        assertEquals("level4", info1.getUpgradeUnitActionInfo().getNewUnitLevel());
        assertEquals(10, info1.getUpgradeUnitActionInfo().getNumToUpgrade());
    }

    @Test
    public void test_create_upgrade_spy_unit() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createUpgradeSpyUnitActionInfo("Player 1", "A", 10);
        assertEquals("Player 1", info1.getSrcOwnerName());
        assertEquals("upgrade spy unit", info1.getActionType());
        assertEquals("A", info1.getUpgradeUnitActionInfo().getSrcName());
        assertEquals("level0", info1.getUpgradeUnitActionInfo().getOldUnitLevel());
        assertEquals("spy", info1.getUpgradeUnitActionInfo().getNewUnitLevel());
        assertEquals(10, info1.getUpgradeUnitActionInfo().getNumToUpgrade());
    }

    @Test
    public void test_create_research_cloaking() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createResearchCloakingActionInfo("Player 1");
        assertEquals("Player 1", info1.getSrcOwnerName());
        assertNull(info1.getUpgradeUnitActionInfo());
    }

    @Test
    public void test_cloaking() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createCloakingActionInfo("Player 1", "Fuqua");
        assertEquals("Player 1", info1.getSrcOwnerName());
        assertEquals("Fuqua", info1.getSrcName());
        assertNull(info1.getTargetTerritoryNames());
    }

    @Test
    public void test_research_patent() {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.creatResearchPatentActionInfo("Player 1", new ArrayList<>());
        assertEquals("Player 1", info1.getSrcOwnerName());
        assertEquals("research patent", info1.getActionType());
        assertEquals(new ArrayList<>(), info1.getTargetTerritoryNames());
        assertEquals(0, info1.getResearchPatentActionInfo().getTargetTerritoryNames().size());
    }
}
