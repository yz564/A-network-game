package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CloakingRuleCheckerTest {
    @Test
    public void test_researchcloaking() {
        CloakingRuleChecker rc = new CloakingRuleChecker(null);
        WorldMapFactory factory = new V2MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
        worldmap.tryAddPlayerInfo(p1);
        Territory t1 = worldmap.getTerritory("Narnia");
        t1.tryAddTroopUnits("level0", 10);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo a1 = af.createCloakingActionInfo("Player 1", "Narnia");
        ActionInfo a2 = af.createUpgradeTechActionInfo("Player 1", 1);
        assertEquals("That action is invalid: cloaking has not been researched yet", rc.checkMyRule(a1, worldmap));
        p1.setTechLevel(3);
        p1.setIsCloakingResearched(true);
        assertNull(rc.checkMyRule(a1, worldmap));
        assertNull(rc.checkMyRule(a2, worldmap));
    }
}
