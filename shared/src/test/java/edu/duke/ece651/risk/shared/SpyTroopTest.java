package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SpyTroopTest {
  @Test
  public void test_constructors() {
    SpyTroop at1 = new SpyTroop("Spy",0, 99999, 0, 0, 20,"Yang");
    Troop at2 = new SpyTroop(7,"Z");
        assertEquals(0, at1.getBonus());
        assertEquals(0, at2.getBonus());
        assertEquals("Spy", at1.getName());
        assertEquals("Spy", at2.getName());
        assertEquals(0, at1.getTechLevelReq());
        assertEquals(0, at2.getTechLevelReq());
        assertEquals(20, at1.getTechCost());
        assertEquals(20, at2.getTechCost());
        assertEquals("Yang", at1.getOwnerName());
        assertEquals("Z", ((SpyTroop)at2).getOwnerName());
  ((SpyTroop)at2).changeOwner("Zhong");
  assertEquals("Zhong", ((SpyTroop)at2).getOwnerName());
    }

}









