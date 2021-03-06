package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class RiskWorldMapTest {
  @Test
  public void test_addterritory() {
    WorldMap worldmap = new RiskWorldMap();
    assertTrue(worldmap.tryAddTerritory("Narnia"));
    assertFalse(worldmap.tryAddTerritory("Narnia"));
    Territory t1 = new BasicTerritory("Narnia", 0);
    assertEquals(t1, worldmap.getTerritory("Narnia"));
  }

  @Test
  public void test_initgroups() {
    WorldMap worldmap = new RiskWorldMap();
    assertTrue(worldmap.tryAddInitGroup(1, "Narnia"));
    assertFalse(worldmap.tryAddInitGroup(1, "Narnia"));
    ArrayList<String> g1 = new ArrayList<String>();
    g1.add("Narnia");
    assertEquals(g1, worldmap.getInitGroup(1));
    assertTrue(worldmap.tryAddInitGroup(1, "Elantris"));
    g1.add("Elantris");
    assertEquals(g1, worldmap.getInitGroup(1));
  }

  @Test
  public void test_assignowner() {
    WorldMap worldmap = new RiskWorldMap();
    assertTrue(worldmap.tryAddTerritory("Narnia"));
    assertTrue(worldmap.tryAddTerritory("Elantris"));
    assertTrue(worldmap.tryAddInitGroup(1, "Narnia"));
    assertTrue(worldmap.tryAddInitGroup(1, "Elantris"));
    assertTrue(worldmap.tryAssignInitOwner(1, "Player 1"));
    assertFalse(worldmap.tryAssignInitOwner(2, "Player 1"));
    assertEquals("Player 1", worldmap.getTerritory("Narnia").getOwnerName());
    assertEquals("Player 1", worldmap.getTerritory("Elantris").getOwnerName());
  }

  @Test
  public void test_playerterritories() {
    WorldMap worldmap = new RiskWorldMap();
    Territory t1 = new BasicTerritory("Narnia", 0);
    Territory t2 = new BasicTerritory("Elantris", 0);
    assertTrue(t1.tryAssignOwner("Player 1"));
    assertTrue(t2.tryAssignOwner("Player 1"));
    HashMap<String, Territory> expected = new HashMap<String, Territory>();
    expected.put(t1.getName(), t1);
    expected.put(t2.getName(), t2);
    assertTrue(worldmap.tryAddTerritory("Narnia"));
    assertTrue(worldmap.tryAddTerritory("Elantris"));
    assertTrue(worldmap.tryAddTerritory("Oz"));
    assertTrue(worldmap.tryChangeOwner("Narnia", "Player 1"));
    assertTrue(worldmap.tryChangeOwner("Elantris", "Player 1"));
    assertTrue(worldmap.tryChangeOwner("Oz", "Player 2"));
    assertFalse(worldmap.tryChangeOwner("Hogwarts", "Player 2"));
    assertEquals(expected, worldmap.getPlayerTerritories("Player 1"));
  }
}
