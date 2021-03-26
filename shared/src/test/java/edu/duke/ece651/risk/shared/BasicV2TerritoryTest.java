package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BasicV2TerritoryTest {
    @Test
    public void test_getters() {
        BasicV2Territory t1 = new BasicV2Territory("ABC", new HashMap<String, Integer>());
        // getMyTroops
        HashMap<String, Troop> myTroops = t1.getMyTroops();
        assertEquals("level1", myTroops.get("level1").getName());
        assertEquals(3, myTroops.get("level1").getTechCost());
        // getName
        assertEquals("ABC", t1.getName());
        // getOwnerName
        assertEquals(null, t1.getOwnerName());
    }
}
