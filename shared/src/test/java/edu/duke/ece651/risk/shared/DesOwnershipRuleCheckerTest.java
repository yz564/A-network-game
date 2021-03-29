package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DesOwnershipRuleCheckerTest {
    @Test
    public void test_desownership() {
        DesOwnershipRuleChecker rc = new DesOwnershipRuleChecker(null);
        WorldMapFactory factory = new V1MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        worldmap.tryAssignInitOwner(2, "Player 2");
        worldmap.tryAssignInitOwner(3, "Player 3");
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("Basic", 3);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo a1 = af.createAttackActionInfo("Player 1", "Narnia", "Elantris", unitNum1);
        ActionInfo a2 = af.createMoveActionInfo("Player 1", "Narnia", "Midkemia", unitNum1);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertEquals(
                "That action in invalid: destination Territory does not belong to a different player",
                rc.checkMyRule(a2, worldmap));
    }
}
