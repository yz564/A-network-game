package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LevelTroopTest {
    @Test
    public void test_constructors(){
        Troop at1 = new LevelTroop("level1", 0, 99999, 0, 0);
        Troop at2 = new LevelTroop("level2", 0, 3, 1);
        assertEquals(0, at1.getBonus());
        assertEquals(3, at2.getBonus());
        assertEquals("level1", at1.getName());
        assertEquals("level2", at2.getName());
        assertEquals(0, at1.getTechLevelReq());
        assertEquals(1, at2.getTechLevelReq());
    }
}
