package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TerritoryExistenceRuleCheckerTest {
    @Test
    public void test_territoryexist() {
        TerritoryExistenceRuleChecker rc = new TerritoryExistenceRuleChecker(null);
        WorldMapFactory factory = new V1MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        ActionInfo a1 = new ActionInfo("Player 1", "move", "Narnia", "Midkemia", 3);
        ActionInfo a2 = new ActionInfo("Player 1", "move", "Test", "Midkemia", 3);
        ActionInfo a3 = new ActionInfo("Player 1", "move", "Narnia", "Test", 3);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertEquals(
                "That action is invalid: source Territory does not exist",
                rc.checkMyRule(a2, worldmap));
        assertEquals(
                "That action is invalid: destination Territory does not exist",
                rc.checkMyRule(a3, worldmap));
    }
}
