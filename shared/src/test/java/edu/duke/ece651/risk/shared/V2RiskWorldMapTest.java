package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2RiskWorldMapTest {
  @Test
  public void test_addterritory() {
    WorldMap worldmap = new V2RiskWorldMap();
    Territory t1 = new V2Territory("Narnia", 0, 0, 0);
    assertTrue(worldmap.tryAddTerritory(t1));
    assertFalse(worldmap.tryAddTerritory(t1));
    assertEquals(t1, worldmap.getTerritory("Narnia"));
  }

  @Test
  public void test_initgroups() {
    WorldMap worldmap = new V2RiskWorldMap();
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
    WorldMap worldmap = new V2RiskWorldMap();
    Territory t1 = new V2Territory("Narnia", 0, 0, 0);
    Territory t2 = new V2Territory("Elantris", 0, 0, 0);
    assertTrue(worldmap.tryAddTerritory(t1));
    assertTrue(worldmap.tryAddTerritory(t2));
    assertTrue(worldmap.tryAddInitGroup(1, "Narnia"));
    assertTrue(worldmap.tryAddInitGroup(1, "Elantris"));
    assertTrue(worldmap.tryAssignInitOwner(1, "Player 1"));
    assertFalse(worldmap.tryAssignInitOwner(2, "Player 1"));
    assertEquals("Player 1", worldmap.getTerritory("Narnia").getOwnerName());
    assertEquals("Player 1", worldmap.getTerritory("Elantris").getOwnerName());
  }

  @Test
  public void test_playerterritories() {
    WorldMap worldmap = new V2RiskWorldMap();
    Territory t1 = new V2Territory("Narnia", 0, 0, 0);
    Territory t2 = new V2Territory("Elantris", 0, 0, 0);
    Territory t3 = new V2Territory("Oz", 0, 0, 0);
    t1.setOwnerName("Player 1");
    t2.setOwnerName("Player 1");
    HashMap<String, Territory> expected = new HashMap<String, Territory>();
    expected.put(t1.getName(), t1);
    expected.put(t2.getName(), t2);
    assertTrue(worldmap.tryAddTerritory(t1));
    assertTrue(worldmap.tryAddTerritory(t2));
    assertTrue(worldmap.tryAddTerritory(t3));
    assertTrue(worldmap.tryChangeOwner("Narnia", "Player 1"));
    assertTrue(worldmap.tryChangeOwner("Elantris", "Player 1"));
    assertTrue(worldmap.tryChangeOwner("Oz", "Player 2"));
    assertFalse(worldmap.tryChangeOwner("Hogwarts", "Player 2"));
    assertEquals(expected, worldmap.getPlayerTerritories("Player 1"));
  }

  @Test
  public void test_playerinfo() {
    WorldMap worldmap = new V2RiskWorldMap();
    PlayerInfo p1 = new PlayerInfo("Player 1", 30, 30);
    PlayerInfo p2 = new PlayerInfo("Player 2", 30, 30);
    PlayerInfo p3 = new PlayerInfo("Player 3", 30, 30);
    assertTrue(worldmap.tryAddPlayerInfo(p1));
    assertTrue(worldmap.tryAddPlayerInfo(p2));
    assertTrue(worldmap.tryAddPlayerInfo(p3));
    assertFalse(worldmap.tryAddPlayerInfo(p1));
    assertEquals(p1.getPlayerName(), worldmap.getPlayerInfo("Player 1").getPlayerName());
    assertEquals(p2.getPlayerName(), worldmap.getPlayerInfo("Player 2").getPlayerName());
    assertEquals(p3.getPlayerName(), worldmap.getPlayerInfo("Player 3").getPlayerName());
  }

  @Test
  public void test_getterritory() {
    WorldMap worldmap = new V2RiskWorldMap();
    Territory t1 = new V2Territory("Narnia", 0, 0, 0);
    Territory t2 = new V2Territory("Elantris", 0, 0, 0);
    worldmap.tryAddTerritory(t1);
    worldmap.tryAddTerritory(t2);
    ArrayList<String> expected = new ArrayList<String>();
    expected.add(t2.getName());
    expected.add(t1.getName());
    assertEquals(expected, worldmap.getMyTerritories());
  }

  @Test
  public void test_inwhichinitgroup(){
    WorldMap worldmap = new V2RiskWorldMap();
    worldmap.tryAddInitGroup(1, "Narnia");
    assertEquals(1, worldmap.inWhichInitGroup("Narnia"));
    assertEquals(0, worldmap.inWhichInitGroup("Elantris"));
  }
}
