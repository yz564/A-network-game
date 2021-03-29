package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DesAdjacencyRuleCheckerTest {
    @Test
    public void test_desadjacency() {
        DesAdjacencyRuleChecker rc = new DesAdjacencyRuleChecker(null);
        WorldMapFactory factory = new V1MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("level0", 3);
        ActionInfo a1 = new ActionInfo("Player 1", "attack", "Narnia", "Elantris", unitNum1);
        ActionInfo a2 = new ActionInfo("Player 1", "attack", "Narnia", "Oz", unitNum1);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertEquals(
                "That action is invalid: destination Territory is not adjacent to source Territory",
                rc.checkMyRule(a2, worldmap));
    }
}
