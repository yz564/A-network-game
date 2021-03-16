package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionExecuterTest {
    @Test
    public void test_send_troops() {
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        map.getTerritory("Western Dothraki Sea").trySetNumUnits(300);
        map.getTerritory("Braavosian Coastlands").trySetNumUnits(100);

        ActionExecuter executer = new ActionExecuter();
        ActionInfo info = new ActionInfo("Western Dothraki Sea", "Braavosian Coastlands", 19);
        executer.sendTroops(map, info);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getNumUnits());
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getNumUnits());
    }
}
