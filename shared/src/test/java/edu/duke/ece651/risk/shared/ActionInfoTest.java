package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ActionInfoTest {
    @Test
    public void test_getters() {
        ActionInfo info = new ActionInfo("AAA", "move");
        assertEquals("AAA", info.getSrcOwnerName());
        assertEquals("move", info.getActionType());
    }

    @Test
    public void test_setters() {
        ActionInfo info = new ActionInfo("AAA", "move");
    }
}
