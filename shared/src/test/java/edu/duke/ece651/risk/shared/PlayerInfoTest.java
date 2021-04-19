package edu.duke.ece651.risk.shared;

import java.util.HashMap;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerInfoTest {
  @Test
  public void test_getters() {
    PlayerInfo p1 = new PlayerInfo("Player 1", 50, 30);
    assertEquals("Player 1", p1.getPlayerName());
    assertEquals(1, p1.getTechLevel());
    HashMap<String, Integer> resources = new HashMap<String, Integer>();
    resources.put("food", 50);
    resources.put("tech", 30);
    assertEquals(resources, p1.getResTotals());
  }

  @Test
  public void test_set_techlevel() {
    PlayerInfo p1 = new PlayerInfo("Player 1", 50, 30);
    p1.setTechLevel(3);
    assertEquals(3, p1.getTechLevel());
  }

  @Test
  public void test_update_restotals() {
    PlayerInfo p1 = new PlayerInfo("Player 1", 50, 30);
    p1.updateOneResTotal("food", 35);
    p1.updateOneResTotal("tech", 15);
    assertEquals(85, p1.getResTotals().get("food"));
    assertEquals(45, p1.getResTotals().get("tech"));

    HashMap<String, Integer> resources = new HashMap<String, Integer>();
    resources.put("food", -10);
    resources.put("tech", -5);
    p1.updateMultiResTotals(resources);
    assertEquals(75, p1.getResTotals().get("food"));
    assertEquals(40, p1.getResTotals().get("tech"));
  }

  @Test
  public void test_getplayerid() {
    PlayerInfo p1 = new PlayerInfo("Player 1", 1, 50, 30);
    assertEquals(1, p1.getPlayerId());
    p1.setPlayerId(3);
    assertEquals(3, p1.getPlayerId());
  }

  @Test
  public void test_player_character() {
    PlayerInfo p1 = new PlayerInfo("Player 1", 1, 50, 30);
    p1.setPlayerId(1);
    assertEquals("Melinda Gates", p1.getCharacterName());
    assertEquals("business", p1.getResearchDomain());

    p1.setPlayerId(2);
    assertEquals("William Kaelin", p1.getCharacterName());
    assertEquals("medicine", p1.getResearchDomain());

    p1.setPlayerId(3);
    assertEquals("Coach K", p1.getCharacterName());
    assertEquals("sports", p1.getResearchDomain());

    p1.setPlayerId(4);
    assertEquals("Drew Hilton", p1.getCharacterName());
    assertEquals("engineering", p1.getResearchDomain());

    p1.setPlayerId(5);
    assertEquals("Valerie Ashby", p1.getCharacterName());
    assertEquals("arts", p1.getResearchDomain());
  }

  @Test
  public void test_cloaking() {
    PlayerInfo p1 = new PlayerInfo("Player 1", 50, 30);
    assertFalse(p1.canCloakingResearched());
    p1.setTechLevel(3);
    assertTrue(p1.canCloakingResearched());
    assertEquals(false, p1.getIsCloakingResearched());
    p1.setIsCloakingResearched(true);
    assertEquals(true, p1.getIsCloakingResearched());
  }

  @Test
  public void test_vizstatus() {
    PlayerInfo p1 = new PlayerInfo("Player 1", 1, 50, 30);
    p1.setOneVizStatus("Narnia", true);
    assertTrue(p1.getOneVizStatus("Narnia"));
    p1.setOneVizStatus("Hogwarts", false);
    assertFalse(p1.getOneVizStatus("Hogwarts"));

    HashSet<String> t1 = new HashSet<>();
    t1.add("Midkemia");
    t1.add("Oz");
    p1.setMultiVizStatus(t1, true);

    HashMap<String, Boolean> expected = new HashMap<>();
    expected.put("Narnia", true);
    expected.put("Hogwarts", false);
    expected.put("Midkemia", true);
    expected.put("Oz", true);
    assertEquals(expected, p1.getAllVizStatus());
  }
}
