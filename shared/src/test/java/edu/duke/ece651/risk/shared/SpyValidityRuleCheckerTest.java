package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SpyValidityRuleCheckerTest {
    @Test
    public void test_spyvalidity() {
        SpyValidityRuleChecker rc = new SpyValidityRuleChecker(null);
        WorldMapFactory factory = new V2MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        worldmap.tryAssignInitOwner(2, "Player 2");
        Territory t1 = worldmap.getTerritory("Narnia");
        t1.tryAddSpyTroopUnits("Player 1", 1);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo a1 = af.createMoveSpyActionInfo("Player 1", "Narnia", "Elantris", 1);
        ActionInfo a2 = af.createMoveSpyActionInfo("Player 1", "Narnia", "Elantris", 10);
        ActionInfo a3 = af.createMoveSpyActionInfo("Player 1", "Midkemia", "Elantris", 1);
        ActionInfo a4 = af.createUpgradeTechActionInfo("Player 1", 2);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertEquals("That action is invalid: source Territory does not contain enough spy units",
                rc.checkMyRule(a2, worldmap));
        assertEquals("That action is invalid: source Territory does not contain your spy units",
                rc.checkMyRule(a3, worldmap));
        assertNull(rc.checkMyRule(a4, worldmap));
    }
}