package edu.duke.ece651.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BasicTroopTest {
    @Test
    public void test_constructor() {
        String troopName = "soldiers";
        BasicTroop t1 = new BasicTroop(5, 100);
        assertEquals(5, t1.getNumUnits());

        assertEquals(true, t1.tryAddUnits(3));
        assertEquals(8, t1.getNumUnits());

        assertEquals(true, t1.tryRemoveUnits(6));
        assertEquals(2, t1.getNumUnits());

        assertEquals(false, t1.tryRemoveUnits(3));
        assertEquals(2, t1.getNumUnits());

        assertEquals(false, t1.tryAddUnits(99));
        assertEquals(2, t1.getNumUnits());

        BasicTroop t2 = new BasicTroop(10);
        assertEquals("Basic", t2.getName());
        assertEquals(10, t2.getNumUnits());

        assertEquals(true, t2.tryAddUnits(989));
        assertEquals(999, t2.getNumUnits());

        assertEquals(true, t2.tryRemoveUnits(999));
        assertEquals(0, t2.getNumUnits());

        assertEquals(false, t2.tryAddUnits(100000));
        assertEquals(0, t2.getNumUnits());

        BasicTroop t3 = new BasicTroop(10, 20);
        assertEquals("Basic", t3.getName());
        assertEquals(10, t3.getNumUnits());

        assertEquals(false, t3.tryRemoveUnits(11));
        assertEquals(10, t3.getNumUnits());

        assertEquals(false, t3.tryAddUnits(11));
        assertEquals(10, t3.getNumUnits());

        assertThrows(IllegalArgumentException.class, () -> new BasicTroop(10, 9));
        assertThrows(IllegalArgumentException.class, () -> new BasicTroop(-1, 9));
    }

    @Test
    public void test_to_string() {
        BasicTroop t1 = new BasicTroop(5, 100);
        assertEquals("Basic with 5 units.\n", t1.toString());

        BasicTroop t2 = new BasicTroop(10);
        assertEquals("Basic with 10 units.\n", t2.toString());
    }

    @Test
    public void test_to_hashcode() {
        BasicTroop t1 = new BasicTroop(5, 100);
        assertEquals(1895559128, t1.hashCode());
    }

    @Test
    public void test_equals() {
        BasicTroop t1 = new BasicTroop(5, 100);
        BasicTroop t2 = new BasicTroop(7, 100);
        assertEquals(false, t2.equals(t1));
        t2.tryRemoveUnits(2);
        assertEquals(true, t2.equals(t1));
        assertEquals(false, t2.equals(4));
    }

    @Test
    public void test_set_num_troops() {
        BasicTroop t1 = new BasicTroop(5, 100);
        assertEquals(true, t1.trySetNumUnits(12));
        assertEquals(12, t1.getNumUnits());
        assertEquals(false, t1.trySetNumUnits(101));
        assertEquals(12, t1.getNumUnits());
    }

    @Test
    public void test_get_bonus_and_techlevelreq() {
        BasicTroop t1 = new BasicTroop(5, 100);
        assertEquals(0, t1.getBonus());
        assertEquals(0, t1.getTechLevelReq());
        assertEquals(0, t1.getTechCost());
    }
}
