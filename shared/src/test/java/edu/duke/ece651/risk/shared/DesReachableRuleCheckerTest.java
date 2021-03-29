package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DesReachableRuleCheckerTest {
    @Test
    public void test_desreachable() {
        DesReachableRuleChecker rc = new DesReachableRuleChecker(null);
        WorldMapFactory factory = new V1MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        worldmap.tryAssignInitOwner(2, "Player 2");
        worldmap.tryAssignInitOwner(3, "Player 3");
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("level0", 3);
        ActionInfo a1 = new ActionInfo("Player 1", "move", "Narnia", "Oz", unitNum1);
        HashMap<String, Integer> unitNum2 = new HashMap<>();
        unitNum2.put("level0", 3);
        ActionInfo a2 = new ActionInfo("Player 1", "move", "Narnia", "Hogwarts", unitNum2);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertEquals(
                "That action is invalid: destination Territory is not reachable from source Territory",
                rc.checkMyRule(a2, worldmap));
    }
}
