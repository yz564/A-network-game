package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTerritoryRuleCheckerTest {
    @Test
    public void test_playerterritory() {
        PlayerTerritoryRuleChecker rc = new PlayerTerritoryRuleChecker(null);
        WorldMapFactory factory = new V2MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        ArrayList<String> targets = new ArrayList<>();
        targets.add("Narnia");
        targets.add("Midkemia");
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo a1 = af.createResearchPatentActionInfo("Player 1", targets);
        assertNull(rc.checkMyRule(a1, worldmap));
        targets.add("Test");
        ActionInfo a2 = af.createResearchPatentActionInfo("Player 1", targets);
        assertEquals("That action is invalid: selected Territory does not exist",
                rc.checkMyRule(a2, worldmap));
        targets.remove("Test");
        ActionInfo a3 = af.createResearchPatentActionInfo("Player 1", targets);
        targets.add("Elantris");
        assertEquals("That action is invalid: selected Territory belongs to a different player",
                rc.checkMyRule(a3, worldmap));
        ActionInfo a4 = af.createUpgradeTechActionInfo("Player 1", 2);
        assertNull(rc.checkMyRule(a4, worldmap));
    }
}