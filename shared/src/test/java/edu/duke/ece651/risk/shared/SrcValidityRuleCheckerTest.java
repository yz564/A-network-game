package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class SrcValidityRuleCheckerTest {
    @Test
    public void test_srcvalidity() {
        SrcValidityRuleChecker rc = new SrcValidityRuleChecker(null);
        WorldMapFactory factory = new V1MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        worldmap.tryAssignInitOwner(2, "Player 2");
        worldmap.tryAssignInitOwner(3, "Player 3");
        Territory t1 = worldmap.getTerritory("Narnia");
        Territory t2 = worldmap.getTerritory("Midkemia");
        t1.tryAddTroopUnits("Basic", 10);
        t2.tryAddTroopUnits("Basic", 12);
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("level0", 3);
        ActionInfo a1 = new ActionInfo("Player 1", "move", "Narnia", "Midkemia", unitNum1);
        ActionInfo a2 = new ActionInfo("Player 1", "move", "Elantris", "Midkemia", unitNum1);
        HashMap<String, Integer> unitNum2 = new HashMap<>();
        unitNum2.put("level0", 11);
        ActionInfo a3 = new ActionInfo("Player 1", "move", "Narnia", "Midkemia", unitNum2);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertEquals(
                "That action is invalid: source Territory belong to a different player",
                rc.checkMyRule(a2, worldmap));
        assertEquals(
                "That action is invalid: source Territory does not contain enough units",
                rc.checkMyRule(a3, worldmap));
    }
}
