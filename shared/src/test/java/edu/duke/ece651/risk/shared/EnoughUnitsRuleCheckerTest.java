package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class EnoughUnitsRuleCheckerTest {
    @Test
    public void test_srcvalidity() {
        EnoughUnitsRuleChecker rc = new EnoughUnitsRuleChecker(null);
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
        unitNum1.put("Basic", 3);
        ActionInfoFactory af = new ActionInfoFactory();
        //ActionInfo a1 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
        //ActionInfo a2 = af.createMoveActionInfo("Player 1", "Elantris", "Midkemia", unitNum1);
        HashMap<String, Integer> unitNum2 = new HashMap<>();
        unitNum2.put("Basic", 11);
        ActionInfo a3 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum2);
        assertEquals(
                "That action is invalid: source Territory does not contain enough Basic units",
                rc.checkMyRule(a3, worldmap));
    }
}
