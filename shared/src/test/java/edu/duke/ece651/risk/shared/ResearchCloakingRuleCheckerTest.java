package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResearchCloakingRuleCheckerTest {
    @Test
    public void test_researchcloaking() {
        ResearchCloakingRuleChecker rc = new ResearchCloakingRuleChecker(null);
        WorldMapFactory factory = new V2MapFactory();
        WorldMap worldmap = factory.makeTestWorldMap();
        worldmap.tryAssignInitOwner(1, "Player 1");
        PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
        worldmap.tryAddPlayerInfo(p1);
        Territory t1 = worldmap.getTerritory("Narnia");
        t1.tryAddTroopUnits("level0", 10);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo a1 = af.createResearchCloakingActionInfo("Player 1");
        ActionInfo a2 = af.createUpgradeTechActionInfo("Player 1", 1);
        assertEquals("That action is invalid: current tech level is not high enough to research cloaking", rc.checkMyRule(a1, worldmap));
        p1.setTechLevel(3);
        assertNull(rc.checkMyRule(a1, worldmap));
        p1.setIsCloakingResearched(true);
        assertEquals("That action is invalid: cloaking has already been researched", rc.checkMyRule(a1, worldmap));
        assertNull(rc.checkMyRule(a2, worldmap));
    }
}