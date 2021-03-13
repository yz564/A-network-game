package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class RiskWorldMapTest {
  @Test
  public void test_addterritory() {
    WorldMap worldmap = new RiskWorldMap();
    worldmap.tryAddTerritory("Narnia");
    Territory t1 = new BasicTerritory("Narnia", 0);
    assertEquals(t1, worldmap.getTerritory("Narnia"));
  }

  @Test
  public void test_initgroups() {
    WorldMap worldmap = new RiskWorldMap();
    worldmap.tryAddInitGroup(1, "Narnia");
    ArrayList<String> g1 = new ArrayList<String>();
    g1.add("Narnia");
    assertEquals(g1, worldmap.getInitGroup(1));
    worldmap.tryAddInitGroup(1, "Elantris");
    g1.add("Elantris");
    assertEquals(g1, worldmap.getInitGroup(1));
  }

  @Test
  public void test_assignowner() {
    WorldMap worldmap = new RiskWorldMap();
    worldmap.tryAddTerritory("Narnia");
    worldmap.tryAddTerritory("Elantris");
    worldmap.tryAddInitGroup(1, "Narnia");
    worldmap.tryAddInitGroup(1, "Elantris");
    worldmap.tryAssignInitOwner(1, "Player 1");
    assertEquals("Player 1", worldmap.getTerritory("Narnia").getOwnerName());
    assertEquals("Player 1", worldmap.getTerritory("Elantris").getOwnerName());
  }
}
