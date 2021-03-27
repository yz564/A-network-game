package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

public class V2TerritoryTest {
  @Test
  public void test_getters() {
    V2Territory t1 = new V2Territory("ABC", new HashMap<String, Integer>(), 10);
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
    V2Territory t1 = new V2Territory("ABC", new HashMap<String, Integer>(), 0);
    HashMap<String, Integer> numUnits = t1.getAllNumUnits();
    assertEquals(0, numUnits.get("level1"));
  }

  @Test
  public void test_add_units() {
    V2Territory t1 = new V2Territory("ABC", new HashMap<String, Integer>(), 0);
    HashMap<String, Troop> myTroops = t1.getMyTroops();
    assertEquals(0, myTroops.get("level1").getNumUnits());
    t1.tryAddTroopUnits("level1", 3);
    assertEquals(3, myTroops.get("level1").getNumUnits());
  }

  @Test
  public void test_remove_units() {
    V2Territory t1 = new V2Territory("ABC", new HashMap<String, Integer>(), 0);
    HashMap<String, Troop> myTroops = t1.getMyTroops();
    t1.tryAddTroopUnits("level1", 3);
    assertEquals(3, myTroops.get("level1").getNumUnits());
    t1.tryRemoveTroopUnits("level1", 2);
    assertEquals(1, myTroops.get("level1").getNumUnits());
  }

  @Test
  public void test_set_units() {
    V2Territory t1 = new V2Territory("ABC", new HashMap<String, Integer>(), 0);
    HashMap<String, Troop> myTroops = t1.getMyTroops();
    t1.tryAddTroopUnits("level1", 3);
    assertEquals(3, myTroops.get("level1").getNumUnits());
    t1.trySetTroopUnits("level1", 2);
    assertEquals(2, myTroops.get("level1").getNumUnits());
  }

  @Test
  public void test_set_all_untis() {
    V2Territory t1 = new V2Territory("ABC", new HashMap<String, Integer>(), 0);
    HashMap<String, Integer> toSet = new HashMap<String, Integer>();
    toSet.put("level0", 5);
    toSet.put("level5", 7);
    t1.trySetNumUnits(toSet);
    assertEquals(5, t1.getTroopNumUnits("level0"));
    assertEquals(7, t1.getTroopNumUnits("level5"));
    toSet.put("Random", 10);
    assertFalse(t1.trySetNumUnits(toSet));
    toSet.remove("Random");
    toSet.put("level0", 1000000000);
    assertFalse(t1.trySetNumUnits(toSet));
  }

  @Test
  public void test_get_troop_num_units() {
    V2Territory t1 = new V2Territory("ABC", new HashMap<String, Integer>(), 0);
    t1.tryAddTroopUnits("level1", 3);
    assertEquals(3, t1.getTroopNumUnits("level1"));
    t1.trySetTroopUnits("level1", 2);
    assertEquals(2, t1.getTroopNumUnits("level1"));
  }

  @Test
  public void test_res_production() {
    V2Territory t1 = new V2Territory("ABC", 1, 2, 0);
    assertEquals(1, t1.getResProduction().get("food"));
    assertEquals(2, t1.getResProduction().get("tech"));
  }
}
