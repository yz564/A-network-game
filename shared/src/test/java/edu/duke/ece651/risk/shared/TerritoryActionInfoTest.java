package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TerritoryActionInfoTest {
    @Test
    public void test_getters() {
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("level0", 123);
        TerritoryActionInfo info = new TerritoryActionInfo("abc", "cde", unitNum1);
        assertEquals("abc", info.getSrcName());
        assertEquals("cde", info.getDesName());
        assertEquals(123, info.getUnitNum().get("level0"));
        assertNotEquals("123", info.getUnitNum().get("level0"));
        assertNotEquals(1234, info.getUnitNum().get("level0"));
    }

    @Test
    public void test_setters() {
        TerritoryActionInfo info = new TerritoryActionInfo();
        assertNull(info.getSrcName());
        assertNull(info.getDesName());
        assertNull(info.getUnitNum());
        info.setSrcName("aaa");
        info.setDesName("bbb");
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("level0", 111);
        info.setUnitNum(unitNum1);
        assertEquals("aaa", info.getSrcName());
        assertEquals("bbb", info.getDesName());
        assertEquals(111, info.getUnitNum().get("level0"));
        assertNotEquals("111", info.getUnitNum().get("level0"));
        assertNotEquals(-111, info.getUnitNum().get("level0"));
    }
}
