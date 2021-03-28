package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionInfoTest {
    @Test
    public void test_getters() {
        ActionInfo info = new ActionInfo("AAA", "move", "abc", "cde", 123);
        assertEquals("abc", info.getSrcName());
        assertEquals("cde", info.getDesName());
        assertEquals(123, info.getUnitNum());
        assertNotEquals("123", info.getUnitNum());
        assertNotEquals(1234, info.getUnitNum());
        assertEquals("AAA", info.getSrcOwnerName());
        assertEquals("move", info.getActionType());
    }

    @Test
    public void test_setters() {
        ActionInfo info = new ActionInfo("AAA", "move");
        assertEquals("move", info.getActionType());
        assertEquals(null, info.getSrcName());
        assertEquals(null, info.getDesName());
        assertEquals(null, info.getUnitNum());
        info.setSrcName("aaa");
        info.setDesName("bbb");
        info.setUnitNum(111);
        assertEquals("aaa", info.getSrcName());
        assertEquals("bbb", info.getDesName());
        assertEquals(111, info.getUnitNum());
        assertNotEquals("111", info.getUnitNum());
        assertNotEquals(-111, info.getUnitNum());
    }
}
