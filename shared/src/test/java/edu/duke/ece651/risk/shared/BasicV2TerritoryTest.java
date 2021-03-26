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

    @Test
    public void test_add_units() {
        BasicV2Territory t1 = new BasicV2Territory("ABC", new HashMap<String, Integer>());
        HashMap<String, Troop> myTroops = t1.getMyTroops();
        assertEquals(0, myTroops.get("level1").getNumUnits());
        HashMap<String, Integer> toAdd = new HashMap<String, Integer>();
        toAdd.put("level1", 3);
        t1.addUnits(toAdd);
        assertEquals(3, myTroops.get("level1").getNumUnits());
    }

    @Test
    public void test_remove_units() {
        BasicV2Territory t1 = new BasicV2Territory("ABC", new HashMap<String, Integer>());
        HashMap<String, Troop> myTroops = t1.getMyTroops();
        HashMap<String, Integer> toAdd = new HashMap<String, Integer>();
        toAdd.put("level1", 3);
        t1.addUnits(toAdd);
        assertEquals(3, myTroops.get("level1").getNumUnits());
        HashMap<String, Integer> toRemove = new HashMap<String, Integer>();
        toRemove.put("level1", 2);
        t1.removeUnits(toRemove);
        assertEquals(1, myTroops.get("level1").getNumUnits());
    }

    @Test
    public void test_set_units() {
        BasicV2Territory t1 = new BasicV2Territory("ABC", new HashMap<String, Integer>());
        HashMap<String, Troop> myTroops = t1.getMyTroops();
        HashMap<String, Integer> toAdd = new HashMap<String, Integer>();
        toAdd.put("level1", 3);
        t1.addUnits(toAdd);
        assertEquals(3, myTroops.get("level1").getNumUnits());
        HashMap<String, Integer> toSet = new HashMap<String, Integer>();
        toSet.put("level1", 2);
        t1.setNumUnits(toSet);
        assertEquals(2, myTroops.get("level1").getNumUnits());
    }
}
