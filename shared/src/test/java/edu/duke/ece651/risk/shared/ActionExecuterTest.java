package edu.duke.ece651.risk.shared;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ActionExecuterTest {
    private WorldMap setupV2Map() {
        // create map
        WorldMapFactory mf = new V2MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        map.tryAddPlayerInfo(new PlayerInfo("Green player", 100, 100));
        map.tryAddPlayerInfo(new PlayerInfo("Blue player", 100, 100));
        map.tryAddPlayerInfo(new PlayerInfo("Red player", 100, 100));
        return map;
    }

    @Test
    public void test_v1_send_troops() {
        ActionInfoFactory af = new ActionInfoFactory();
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
        map.getTerritory("Braavosian Coastlands").trySetTroopUnits("Basic", 100);
        ActionExecuter executer = new ActionExecuter();
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("Basic", 19);
        ActionInfo info =
                af.createAttackActionInfo(
                        "Green player", "Western Dothraki Sea", "Braavosian Coastlands", unitNum1);
        executer.sendTroops(map, info);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
    }

    @Test
    public void test_v2_send_troops() {
        WorldMap map = setupV2Map();
        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 300);
        numUnits1.put("level5", 300);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level5", 100);
        map.getTerritory("LSRC").trySetNumUnits(numUnits2);
        // execution
        ActionExecuter executer = new ActionExecuter();
        HashMap<String, Integer> toAttack = new HashMap<>();
        toAttack.put("level6", 19);
        toAttack.put("level5", 9);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info = af.createAttackActionInfo("Green player", "Gross Hall", "LSRC", toAttack);
        executer.sendTroops(map, info);
        assertEquals(281, map.getTerritory("Gross Hall").getTroopNumUnits("level6"));
        assertEquals(291, map.getTerritory("Gross Hall").getTroopNumUnits("level5"));
        assertEquals(0, map.getTerritory("LSRC").getTroopNumUnits("level6"));
        assertEquals(100, map.getTerritory("LSRC").getTroopNumUnits("level5"));
    }

    @Test
    public void test_execute_pre_attack() {
        WorldMap map = setupV2Map();
        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 300);
        numUnits1.put("level5", 300);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level5", 100);
        map.getTerritory("LSRC").trySetNumUnits(numUnits2);
        // execution
        ActionExecuter executer = new ActionExecuter();
        HashMap<String, Integer> toAttack = new HashMap<>();
        toAttack.put("level6", 19);
        toAttack.put("level5", 9);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info = af.createAttackActionInfo("Green player", "Gross Hall", "LSRC", toAttack);
        executer.executePreAttack(map, info);
        assertEquals(100 - 28, map.getPlayerInfo("Green player").getResTotals().get("food"));
    }

    @Disabled
    @Test
    public void test_v1_execute_move() {
        ActionInfoFactory af = new ActionInfoFactory();
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
        map.getTerritory("Braavosian Coastlands").trySetTroopUnits("Basic", 100);

        ActionExecuter executer = new ActionExecuter();
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("Basic", 19);
        ActionInfo info1 =
                af.createMoveActionInfo(
                        "Green player", "Western Dothraki Sea", "Braavosian Coastlands", unitNum1);
        executer.executeMove(map, info1);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals(119, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));

        HashMap<String, Integer> unitNum2 = new HashMap<>();
        unitNum2.put("Basic", 19);
        ActionInfo info2 =
                af.createMoveActionInfo(
                        "Green player", "Braavosian Coastlands", "Western Dothraki Sea", unitNum2);
        executer.executeMove(map, info2);
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
    }

    @Disabled
    @Test
    public void test_v2_execute_move() {
        WorldMap map = setupV2Map();
        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 300);
        numUnits1.put("level5", 300);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level5", 100);
        map.getTerritory("LSRC").trySetNumUnits(numUnits2);
        // execution
        ActionExecuter executer = new ActionExecuter();
        HashMap<String, Integer> toMove = new HashMap<>();
        toMove.put("level6", 19);
        toMove.put("level5", 9);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info = af.createAttackActionInfo("Green player", "Gross Hall", "LSRC", toMove);
        executer.executeMove(map, info);
        assertEquals(281, map.getTerritory("Gross Hall").getTroopNumUnits("level6"));
        assertEquals(291, map.getTerritory("Gross Hall").getTroopNumUnits("level5"));
        assertEquals(19, map.getTerritory("LSRC").getTroopNumUnits("level6"));
        assertEquals(109, map.getTerritory("LSRC").getTroopNumUnits("level5"));
        assertEquals(100 - 28 * 6, map.getPlayerInfo("Green player").getResTotals().get("food"));
        assertEquals(100, map.getPlayerInfo("Green player").getResTotals().get("tech"));
    }

    @Disabled
    @Test
    public void test_v1_execute_attack() {
        ActionInfoFactory af = new ActionInfoFactory();
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        ActionExecuter executer0 = new ActionExecuter(10);
        ActionExecuter executer = new ActionExecuter();

        map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
        map.getTerritory("Myr").trySetTroopUnits("Basic", 100);
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        unitNum1.put("Basic", 300);
        ActionInfo info1 =
                af.createAttackActionInfo("Green player", "Western Dothraki Sea", "Myr", unitNum1);
        HashMap<String, Integer> unitNum2 = new HashMap<>();
        unitNum2.put("Basic", 100);
        ActionInfo info2 =
                af.createAttackActionInfo("Blue player", "Myr", "Western Dothraki Sea", unitNum2);
        executer.sendTroops(map, info1);
        assertEquals(0, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals("Green player", map.getTerritory("Western Dothraki Sea").getOwnerName());
        executer.sendTroops(map, info2);
        assertEquals(0, map.getTerritory("Myr").getTroopNumUnits("Basic"));
        assertEquals("Blue player", map.getTerritory("Myr").getOwnerName());
        executer.executeAttack(map, info1);
        assertEquals(300, map.getTerritory("Myr").getTroopNumUnits("Basic"));
        assertEquals("Green player", map.getTerritory("Myr").getOwnerName());
        executer.executeAttack(map, info2);
        assertEquals(100, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals("Blue player", map.getTerritory("Western Dothraki Sea").getOwnerName());

        map.getTerritory("Braavosian Coastlands").trySetTroopUnits("Basic", 300);
        map.getTerritory("Lower Rnoyne").trySetTroopUnits("Basic", 3);

        ActionInfo info3 =
                af.createAttackActionInfo(
                        "Green player", "Braavosian Coastlands", "Lower Rnoyne", unitNum1);
        assertEquals(300, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
        assertEquals(3, map.getTerritory("Lower Rnoyne").getTroopNumUnits("Basic"));
        assertEquals("Blue player", map.getTerritory("Lower Rnoyne").getOwnerName());
        executer.sendTroops(map, info3);
        executer.executeAttack(map, info3);
        assertEquals("Green player", map.getTerritory("Braavosian Coastlands").getOwnerName());
        assertEquals(0, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
        assertEquals("Green player", map.getTerritory("Lower Rnoyne").getOwnerName());
        assertEquals(298, map.getTerritory("Lower Rnoyne").getTroopNumUnits("Basic"));

        // defender wins
        map.getTerritory("Forest of Qohor").trySetTroopUnits("Basic", 5);
        map.getTerritory("Lhaxar").trySetTroopUnits("Basic", 500);

        HashMap<String, Integer> unitNum3 = new HashMap<>();
        unitNum3.put("Basic", 3);
        ActionInfo info4 =
                af.createAttackActionInfo("Green player", "Forest of Qohor", "Lhaxar", unitNum3);
        executer0.sendTroops(map, info4);
        executer0.executeAttack(map, info4);
        assertEquals("Blue player", map.getTerritory("Lhaxar").getOwnerName());
        assertEquals(497, map.getTerritory("Lhaxar").getTroopNumUnits("Basic"));
    }
}
