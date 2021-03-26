package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class BasicTerritoryTest {
    @Test
    public void test_constructor() {
        BasicTroop troop = new BasicTroop(10, 100);
        BasicTerritory territory = new BasicTerritory("Winterfell", troop);
        assertEquals("Winterfell", territory.getName());

        int toAdd = 34;
        troop.tryAddUnits(toAdd);
        territory.tryAddUnits(toAdd);
        assertEquals(true, troop.equals(territory.getTroop()));

        int troopUnits = troop.getNumUnits();
        int territoryUnits = territory.getNumUnits();
        assertEquals(troopUnits, territoryUnits);

        int toRemove = 20;
        troop.tryRemoveUnits(toRemove);
        territory.tryRemoveUnits(toRemove);
        assertEquals(true, troop.equals(territory.getTroop()));
        assertEquals(troop.getNumUnits(), territory.getTroop().getNumUnits());

        territory.tryRemoveUnits(100);
        assertEquals(true, troop.equals(territory.getTroop()));
        assertEquals(troop.getNumUnits(), territory.getTroop().getNumUnits());

        BasicTroop troop2 = new BasicTroop(5);
        BasicTerritory territory2 = new BasicTerritory("North", 5);
        assertEquals("North", territory2.getName());

        int addThree = 3;
        troop2.tryAddUnits(addThree);
        territory2.tryAddUnits(addThree);
        assertEquals(true, troop2.equals(territory2.getTroop()));
        assertEquals(troop2.getNumUnits(), territory2.getTroop().getNumUnits());

        BasicTroop troop3 = new BasicTroop(5, 40);
        BasicTerritory territory3 = new BasicTerritory("North", 5);
        assertEquals("North", territory3.getName());
        assertEquals(true, troop3.equals(territory3.getTroop()));
    }

    @Test
    public void test_equals() {
        BasicTerritory territory1 = new BasicTerritory("North", new BasicTroop(5, 100));
        BasicTerritory territory2 = new BasicTerritory("North", 5);
        BasicTerritory territory3 = new BasicTerritory("North", 5);

        assertEquals(true, territory1.equals(territory2));
        assertEquals(true, territory2.equals(territory3));
        assertEquals(true, territory1.equals(territory3));
        assertEquals(false, territory1.equals(new BasicTroop(5)));
    }

    /*
     * @Test public void test_to_string() { BasicTerritory territory = new
     * BasicTerritory("North", 2); String output =
     * "Territory North contains the following troop.\nTroop with 2 units.\n";
     * assertEquals(true, territory.toString().equals(output)); }
     */

    @Test
    public void test_hash() {
        BasicTerritory territory = new BasicTerritory("Narnia", 10);
        assertEquals(524983420, territory.hashCode());
    }

    @Test
    public void test_set_num_units() {
        BasicTerritory t = new BasicTerritory("Narnia", 10);
        assertEquals(true, t.trySetNumUnits(100));
        assertEquals(100, t.getNumUnits());
        assertEquals(false, t.trySetNumUnits(100000));
        assertEquals(100, t.getNumUnits());
    }

    @Test
    public void test_adjacency() {
        BasicTerritory t1 = new BasicTerritory("Narnia", 10);
        BasicTerritory t2 = new BasicTerritory("Elantris", 10);
        assertFalse(t1.isAdjacentTo(t2));
        assertTrue(t1.tryAddNeighbor(t2));
        assertFalse(t1.tryAddNeighbor(t2));
        assertTrue(t1.isAdjacentTo(t2));
    }

    @Test
    public void test_owner() {
        BasicTerritory t1 = new BasicTerritory("Narnia", 10);
        assertFalse(t1.isBelongTo("Player 1"));
        assertTrue(t1.tryAssignOwner("Player 1"));
        assertFalse(t1.tryAssignOwner(null));
        assertTrue(t1.isBelongTo("Player 1"));
        assertEquals("Player 1", t1.getOwnerName());
    }

    @Test
    public void test_get_my_neighbors() {
        BasicTerritory t1 = new BasicTerritory("Narnia", 10);
        HashMap<String, Territory> expectedNeighbors = new HashMap<String, Territory>();

        BasicTerritory t2 = new BasicTerritory("Elantris", 10);
        BasicTerritory t3 = new BasicTerritory("North", 10);

        expectedNeighbors.put("Elantris", t2);
        expectedNeighbors.put("North", t3);
        assertTrue(t1.tryAddNeighbor(t2));
        assertTrue(t1.tryAddNeighbor(t3));

        assertEquals(expectedNeighbors, t1.getMyNeighbors());
    }

    @Test
    public void test_isreachable() {
        BasicTerritory t1 = new BasicTerritory("Narnia", 10);
        BasicTerritory t2 = new BasicTerritory("Midkemia", 10);
        BasicTerritory t3 = new BasicTerritory("Oz", 10);
        BasicTerritory t4 = new BasicTerritory("Hogwarts", 10);
        t1.tryAssignOwner("Player 1");
        t2.tryAssignOwner("Player 1");
        t3.tryAssignOwner("Player 1");
        t4.tryAssignOwner("Player 2");
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
    public void test_isreachable2() {
        WorldMapFactory factory = new V1MapFactory();
        WorldMap map = factory.makeTestWorldMap();
        map.tryAssignInitOwner(1, "Player 1");
        map.tryAssignInitOwner(2, "Player 2");
        map.tryAssignInitOwner(3, "Player 3");
        Territory t1 = map.getTerritory("Narnia");
        Territory t2 = map.getTerritory("Oz");
        assertTrue(t1.isReachableTo(t2));
    }

    @Test
    public void test_isreachable3() {
        WorldMapFactory factory = new V1MapFactory();
        WorldMap map = factory.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Player 1");
        map.tryAssignInitOwner(2, "Player 2");
        map.tryAssignInitOwner(3, "Player 3");
        Territory t1 = map.getTerritory("Western Dothraki Sea");
        Territory t2 = map.getTerritory("Braavosian Coastlands");
        assertTrue(t1.isReachableTo(t2));
    }
}
