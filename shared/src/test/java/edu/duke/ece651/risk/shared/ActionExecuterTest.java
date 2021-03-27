package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class ActionExecuterTest {
    @Test
    public void test_send_troops() {
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        HashMap<String, Integer> toAdd1 = new HashMap<>();
        toAdd1.put("level0", 300);
        map.getTerritory("Western Dothraki Sea").setNumUnits(toAdd1);
        HashMap<String, Integer> toAdd2 = new HashMap<>();
        toAdd2.put("level0", 100);
        map.getTerritory("Braavosian Coastlands").setNumUnits(toAdd2);

        ActionExecuter executer = new ActionExecuter();
        ActionInfo info = new ActionInfo("Green player", "Western Dothraki Sea", "Braavosian Coastlands", 19);
        executer.sendTroops(map, info);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));
    }

    @Test
    public void test_execute_move() {
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");

        HashMap<String, Integer> toAdd1 = new HashMap<>();
        toAdd1.put("level0", 300);
        map.getTerritory("Western Dothraki Sea").setNumUnits(toAdd1);
        HashMap<String, Integer> toAdd2 = new HashMap<>();
        toAdd2.put("level0", 100);
        map.getTerritory("Braavosian Coastlands").setNumUnits(toAdd2);

        ActionExecuter executer = new ActionExecuter();
        ActionInfo info1 = new ActionInfo("Green player", "Western Dothraki Sea", "Braavosian Coastlands", 19);
        executer.executeMove(map, info1);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals(119, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));

        ActionInfo info2 = new ActionInfo("Green player", "Braavosian Coastlands", "Western Dothraki Sea", 19);
        executer.executeMove(map, info2);
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));
    }

    @Test
    public void test_execute_attack() {
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        ActionExecuter executer0 = new ActionExecuter(10);
        ActionExecuter executer = new ActionExecuter();

        HashMap<String, Integer> toAdd1 = new HashMap<>();
        toAdd1.put("level0", 300);
        map.getTerritory("Western Dothraki Sea").setNumUnits(toAdd1);
        HashMap<String, Integer> toAdd2 = new HashMap<>();
        toAdd2.put("level0", 100);
        map.getTerritory("Myr").setNumUnits(toAdd2);
        ActionInfo info1 = new ActionInfo("Green player", "Western Dothraki Sea", "Myr", 300);
        ActionInfo info2 = new ActionInfo("Blue player", "Myr", "Western Dothraki Sea", 100);
        executer.sendTroops(map, info1);
        assertEquals(0, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals("Green player", map.getTerritory("Western Dothraki Sea").getOwnerName());
        executer.sendTroops(map, info2);
        assertEquals(0, map.getTerritory("Myr").getTroopNumUnits("level0"));
        assertEquals("Blue player", map.getTerritory("Myr").getOwnerName());
        executer.executeAttack(map, info1);
        assertEquals(300, map.getTerritory("Myr").getTroopNumUnits("level0"));
        assertEquals("Green player", map.getTerritory("Myr").getOwnerName());
        executer.executeAttack(map, info2);
        assertEquals(100, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals("Blue player", map.getTerritory("Western Dothraki Sea").getOwnerName());

        HashMap<String, Integer> toAdd3 = new HashMap<>();
        toAdd3.put("level0", 300);
        map.getTerritory("Braavosian Coastlands").setNumUnits(toAdd3);
        HashMap<String, Integer> toAdd4 = new HashMap<>();
        toAdd4.put("level0", 3);
        map.getTerritory("Lower Rnoyne").setNumUnits(toAdd4);

        ActionInfo info3 = new ActionInfo("Green player", "Braavosian Coastlands", "Lower Rnoyne", 300);
        assertEquals(300, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));
        assertEquals(3, map.getTerritory("Lower Rnoyne").getTroopNumUnits("level0"));
        assertEquals("Blue player", map.getTerritory("Lower Rnoyne").getOwnerName());
        executer.sendTroops(map, info3);
        executer.executeAttack(map, info3);
        assertEquals("Green player", map.getTerritory("Braavosian Coastlands").getOwnerName());
        assertEquals(0, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));
        assertEquals("Green player", map.getTerritory("Lower Rnoyne").getOwnerName());
        assertEquals(298, map.getTerritory("Lower Rnoyne").getTroopNumUnits("level0"));

        // defender wins
        HashMap<String, Integer> toAdd5 = new HashMap<>();
        toAdd5.put("level0", 5);
        map.getTerritory("Forest of Qohor").setNumUnits(toAdd5);
        HashMap<String, Integer> toAdd6 = new HashMap<>();
        toAdd6.put("level0", 500);
        map.getTerritory("Lhaxar").setNumUnits(toAdd6);

        ActionInfo info4 = new ActionInfo("Green player", "Forest of Qohor", "Lhaxar", 3);
        executer0.sendTroops(map, info4);
        executer0.executeAttack(map, info4);
        assertEquals("Blue player", map.getTerritory("Lhaxar").getOwnerName());
        assertEquals(497, map.getTerritory("Lhaxar").getTroopNumUnits("level0"));
    }
}
