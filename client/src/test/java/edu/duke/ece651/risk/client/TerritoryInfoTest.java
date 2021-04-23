package edu.duke.ece651.risk.client;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryInfoTest {

    @Test
    void test_getters() {
        HashMap<String, Integer> resProduction = new HashMap<>();
        resProduction.put("tech", 3);
        resProduction.put("food", 4);
        TerritoryInfo info = new TerritoryInfo("Player 1", "Pratt", 3, "arts", resProduction);
        assertEquals(0, info.getTotalTroopNum());
        assertEquals(0, info.getCloakingTurns());
        assertEquals("Arts", info.getDomain());
        assertEquals("Pratt", info.getTerritoryName());
        assertEquals("Unknown", info.getOwnerName());
        assertEquals(4, info.getFoodProduction());
        assertEquals(3, info.getTechProduction());
        assertEquals(3, info.getSize());
        assertEquals(0, info.getOneTroopNum("level0"));
        assertEquals(0, info.getPlayerSpyNum());
    }

    @Test
    void test_setters() {
        TerritoryInfo info = new TerritoryInfo("Player 1", "Pratt", "arts", 2, 2, 5);
        info.setCloakingTurns(3);
        assertEquals(0, info.getCloakingTurns());
        info.setOwnerName("Player 1");
        assertEquals(3, info.getCloakingTurns());
        info.setPlayerSpyNum(4);
        assertEquals(4, info.getPlayerSpyNum());
        info.setTroopNum(new HashMap<>());
        assertEquals(0, info.getTotalTroopNum());
    }
}
