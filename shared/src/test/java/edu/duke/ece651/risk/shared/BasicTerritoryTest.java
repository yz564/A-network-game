package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BasicTerritoryTest {
    @Test
    public void test_getters() {
        BasicTerritory t1 = new BasicTerritory("ABC", new HashMap<String, Integer>(), 10);
        // getMyTroops
        HashMap<String, Troop> myTroops = t1.getMyTroops();
        assertEquals("level1", myTroops.get("level1").getName());
        assertEquals(3, myTroops.get("level1").getTechCost());
        // getName
        assertEquals("ABC", t1.getName());
        // getOwnerName
        assertEquals(null, t1.getOwnerName());
        // getSize
        assertEquals(10, t1.getSize());
    }

    @Test
    public void test_get_all_num_units() {
        BasicTerritory t1 = new BasicTerritory("ABC", new HashMap<String, Integer>(), 0);
        HashMap<String, Integer> numUnits = t1.getAllNumUnits();
        assertEquals(0, numUnits.get("level1"));
    }

    @Test
    public void test_add_units() {
        BasicTerritory t1 = new BasicTerritory("ABC", new HashMap<String, Integer>(), 0);
        HashMap<String, Troop> myTroops = t1.getMyTroops();
        assertEquals(0, myTroops.get("level1").getNumUnits());
        HashMap<String, Integer> toAdd = new HashMap<String, Integer>();
        toAdd.put("level1", 3);
        t1.addUnits(toAdd);
        assertEquals(3, myTroops.get("level1").getNumUnits());
    }

    @Test
    public void test_remove_units() {
        BasicTerritory t1 = new BasicTerritory("ABC", new HashMap<String, Integer>(), 0);
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
        BasicTerritory t1 = new BasicTerritory("ABC", new HashMap<String, Integer>(), 0);
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

    @Test
    public void test_get_troop_num_units() {
        BasicTerritory t1 = new BasicTerritory("ABC", new HashMap<String, Integer>(), 0);
        HashMap<String, Integer> toAdd = new HashMap<String, Integer>();
        toAdd.put("level1", 3);
        t1.addUnits(toAdd);
        assertEquals(3, t1.getTroopNumUnits("level1"));
        HashMap<String, Integer> toSet = new HashMap<String, Integer>();
        toSet.put("level1", 2);
        t1.setNumUnits(toSet);
        assertEquals(2, t1.getTroopNumUnits("level1"));
    }

    @Test
    public void test_adjacency() {
        Territory t1 = new BasicTerritory("Narnia", new HashMap<>(), 0);
        Territory t2 = new BasicTerritory("Elantris", new HashMap<>(), 0);
        assertFalse(t1.isAdjacentTo(t2));
        assertTrue(t1.tryAddNeighbor(t2));
        assertFalse(t1.tryAddNeighbor(t2));
        assertTrue(t1.isAdjacentTo(t2));
    }

    @Test
    public void test_get_my_neighbors() {
        Territory t1 = new BasicTerritory("Narnia", new HashMap<>(), 0);
        HashMap<String, Territory> expectedNeighbors = new HashMap<String, Territory>();

        Territory t2 = new BasicTerritory("Elantris", new HashMap<>(), 0);
        Territory t3 = new BasicTerritory("North", new HashMap<>(), 0);

        expectedNeighbors.put("Elantris", t2);
        expectedNeighbors.put("North", t3);
        assertTrue(t1.tryAddNeighbor(t2));
        assertTrue(t1.tryAddNeighbor(t3));

        assertEquals(expectedNeighbors, t1.getMyNeighbors());
    }

    @Test
    public void test_owner() {
        Territory t1 = new BasicTerritory("Narnia", new HashMap<>(), 0);
        assertFalse(t1.isBelongTo("Player 1"));
        t1.putOwnerName("Player 1");
        assertTrue(t1.isBelongTo("Player 1"));
        assertEquals("Player 1", t1.getOwnerName());
    }

    @Test
    public void test_isreachable() {
        BasicTerritory t1 = new BasicTerritory("Narnia", new HashMap<>(), 0);
        BasicTerritory t2 = new BasicTerritory("Midkemia", new HashMap<>(), 0);
        BasicTerritory t3 = new BasicTerritory("Oz", new HashMap<>(), 0);
        BasicTerritory t4 = new BasicTerritory("Hogwarts", new HashMap<>(), 0);
        t1.putOwnerName("Player 1");
        t2.putOwnerName("Player 1");
        t3.putOwnerName("Player 1");
        t4.putOwnerName("Player 2");
        t1.tryAddNeighbor(t2);
        t2.tryAddNeighbor(t1);
        t2.tryAddNeighbor(t3);
        t3.tryAddNeighbor(t2);
        t1.tryAddNeighbor(t4);
        assertTrue(t1.isReachableTo(t2));
        assertTrue(t1.isReachableTo(t3));
        assertFalse(t1.isReachableTo(t4));
    }

    @Test
    public void test_res_production() {
        BasicTerritory t1 = new BasicTerritory("ABC", 1, 2, 0);
        assertEquals(1, t1.getResProduction().get("food"));
        assertEquals(2, t1.getResProduction().get("tech"));
    }
}
