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

    @Test
    public void test_execute_move() {
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        map.getTerritory("Western Dothraki Sea").trySetNumUnits(300);
        map.getTerritory("Braavosian Coastlands").trySetNumUnits(100);

        ActionExecuter executer = new ActionExecuter();
        ActionInfo info1 = new ActionInfo("Western Dothraki Sea", "Braavosian Coastlands", 19);
        executer.executeMove(map, info1);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getNumUnits());
        assertEquals(119, map.getTerritory("Braavosian Coastlands").getNumUnits());

        ActionInfo info2 = new ActionInfo("Braavosian Coastlands", "Western Dothraki Sea", 19);
        executer.executeMove(map, info2);
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getNumUnits());
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getNumUnits());
    }
}
