package edu.duke.ece651.risk.shared;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

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
        ActionInfo info1 = af.createUpgradeUnitActionInfo("Player 1", "A", 2, 4, 10);
        assertEquals("Player 1", info1.getSrcOwnerName());
        assertEquals("upgrade unit", info1.getActionType());
        assertEquals("A", info1.getUpgradeUnitActionInfo().getSrcName());
        assertEquals(2, info1.getUpgradeUnitActionInfo().getOldUnitLevel());
        assertEquals(4, info1.getUpgradeUnitActionInfo().getNewUnitLevel());
        assertEquals(10, info1.getUpgradeUnitActionInfo().getNumToUpgrade());
    }
}