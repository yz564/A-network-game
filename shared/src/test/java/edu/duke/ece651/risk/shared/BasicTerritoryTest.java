package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicTerritoryTest {
  @Test
  public void test_constructor() {
    BasicTroop troop = new BasicTroop("Soldiers", 10, 100);
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

    BasicTroop troop3 = new BasicTroop("Knights", 5, 40);
    BasicTerritory territory3 = new BasicTerritory("North", 5);
    assertEquals("North", territory3.getName());
    assertEquals(false, troop3.equals(territory3.getTroop()));
  }

  @Test
  public void test_equals() {
    BasicTerritory territory1 = new BasicTerritory("North", new BasicTroop("Dragons", 5, 100));
    BasicTerritory territory2 = new BasicTerritory("North", 5);
    BasicTerritory territory3 = new BasicTerritory("North", 5);

    assertEquals(false, territory1.equals(territory2));
    assertEquals(true, territory2.equals(territory3));
    assertEquals(false, territory1.equals(territory3));
    assertEquals(false, territory1.equals(new BasicTroop(5)));
  }

  /*
  @Test
  public void test_to_string() {
    BasicTerritory territory = new BasicTerritory("North", 2);
    String output = "Territory North contains the following troop.\nTroop with 2 units.\n";
    assertEquals(true, territory.toString().equals(output));
  }
  */

  @Test
  public void test_hash() {
    BasicTerritory territory = new BasicTerritory("Narnia", 10);
    assertEquals(-517134432, territory.hashCode());
  }
}
