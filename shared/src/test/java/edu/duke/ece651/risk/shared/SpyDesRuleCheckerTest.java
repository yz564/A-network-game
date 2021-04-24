package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class SpyDesRuleCheckerTest {
    @Test
    public void test_spydes() {
        SpyDesRuleChecker rc = new SpyDesRuleChecker(null);
        WorldMapFactory factory = new V2MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        worldmap.tryAssignInitOwner(2, "Player 2");
        worldmap.tryAssignInitOwner(3, "Player 3");
        Territory t1 = worldmap.getTerritory("Narnia");
        Territory t2 = worldmap.getTerritory("Midkemia");
        t1.tryAddSpyTroopUnits("Player 1", 1);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo a1 = af.createMoveSpyActionInfo("Player 1", "Narnia", "Elantris", 1);
        t2.setOwnerName("Player 2");
        ActionInfo a2 = af.createMoveSpyActionInfo("Player 1", "Narnia", "Oz", 1);
        ActionInfo a3 = af.createMoveSpyActionInfo("Player 1", "Narnia", "Hogwarts", 1);
        ActionInfo a4 = af.createUpgradeTechActionInfo("Player 1", 2);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertEquals(
                "That action is invalid: destination own Territory is not reachable from source Territory",
                rc.checkMyRule(a2, worldmap));
        assertEquals(
                "That action is invalid: destination enemy Territory is not adjacent to source Territory",
                rc.checkMyRule(a3, worldmap));
        assertNull(rc.checkMyRule(a4, worldmap));
        t2.setOwnerName("Player 1");
        ActionInfo a5 = af.createMoveSpyActionInfo("Player 1", "Narnia", "Midkemia", 1);
        assertNull(rc.checkMyRule(a5, worldmap));
    }
}
