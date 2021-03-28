package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class BasicTerritoryTest {
  @Test
  public void test_constructor() {
    BasicTroop troop = new BasicTroop(10, 100);
    BasicTerritory territory = new BasicTerritory("Narnia", 10);
    assertEquals("Narnia", territory.getName());
    assertEquals(troop.getName(), territory.getTroop("Basic").getName());

    int troopUnits = troop.getNumUnits();
    int territoryUnits = territory.getTroopNumUnits("Basic");
    assertEquals(troopUnits, territoryUnits);
    assertEquals(true, troop.equals(territory.getTroop("Basic")));
    assertEquals(troop.getNumUnits(), territory.getTroopNumUnits("Basic"));

    BasicTroop troop2 = new BasicTroop(5);
    BasicTerritory territory2 = new BasicTerritory("Elantris", 5);
    assertEquals("Elantris", territory2.getName());

    int addThree = 3;
    troop2.tryAddUnits(addThree);
    territory2.tryAddTroopUnits("Basic", addThree);
    assertEquals(true, troop2.equals(territory2.getTroop("Basic")));
    assertEquals(troop2.getNumUnits(), territory2.getTroopNumUnits("Basic"));

    BasicTroop troop3 = new BasicTroop(5, 40);
    BasicTerritory territory3 = new BasicTerritory("Oz", 10);
    assertEquals("Oz", territory3.getName());
    assertEquals(false, troop3.equals(territory3.getTroop("Basic")));
  }

  @Test
  public void test_add_units() {
    BasicTerritory t1 = new BasicTerritory("Narnia", 10);
    assertTrue(t1.tryAddTroopUnits("Basic", 15));
    assertFalse(t1.tryAddTroopUnits("Advanced", 15));
    assertEquals(25, t1.getTroopNumUnits("Basic"));
  }

  @Test
  public void test_remove_units() {
    BasicTerritory t1 = new BasicTerritory("Narnia", 10);
    assertTrue(t1.tryRemoveTroopUnits("Basic", 5));
    assertFalse(t1.tryRemoveTroopUnits("Advanced", 5));
    assertEquals(5, t1.getTroopNumUnits("Basic"));
  }

  @Test
  public void test_set_units() {
    BasicTerritory t1 = new BasicTerritory("Narnia", 10);
    assertTrue(t1.trySetTroopUnits("Basic", 8));
    assertFalse(t1.trySetTroopUnits("Advanced", 8));
    assertEquals(8, t1.getTroopNumUnits("Basic"));
  }

  @Test
  public void test_equals() {
    HashMap<String, Troop> troops = new HashMap<String, Troop>();
    troops.put("Basic", new BasicTroop(5));
    BasicTerritory territory1 = new BasicTerritory("Narnia", troops);
    BasicTerritory territory2 = new BasicTerritory("Narnia", 5);
    BasicTerritory territory3 = new BasicTerritory("Narnia");

    assertEquals(false, territory1.equals(territory3));
    assertEquals(true, territory1.equals(territory2));
    assertEquals(false, territory2.equals(territory3));
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
    assertEquals(217368665, territory.hashCode());
  }

  @Test
  public void test_set_num_units() {
    BasicTerritory t = new BasicTerritory("Narnia", 10);
    assertEquals(true, t.trySetTroopUnits("Basic", 100));
    assertEquals(100, t.getTroopNumUnits("Basic"));
    assertEquals(false, t.trySetTroopUnits("Basic", 100000));
    assertEquals(100, t.getTroopNumUnits("Basic"));
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
    assertNull(t1.getOwnerName());
    t1.setOwnerName("Player 1");
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
    t1.setOwnerName("Player 1");
    t2.setOwnerName("Player 1");
    t3.setOwnerName("Player 1");
    t4.setOwnerName("Player 2");
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

  @Test
  public void test_size_resource() {
    Territory t1 = new BasicTerritory("Narnia", 10);
    assertEquals(0, t1.getSize());
    assertNull(t1.getResProduction());
  }
}
