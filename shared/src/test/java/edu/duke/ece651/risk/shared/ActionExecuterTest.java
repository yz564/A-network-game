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
        ActionInfo info = af.createAttackActionInfo("Blue player", "Gross Hall", "LSRC", toAttack);
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
        ActionInfo info = af.createAttackActionInfo("Blue player", "Gross Hall", "LSRC", toAttack);
        executer.executePreAttack(map, info);
        assertEquals(100 - 28, map.getPlayerInfo("Blue player").getResTotals().get("food"));
    }

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
        ActionInfo info = af.createMoveActionInfo("Blue player", "Gross Hall", "LSRC", toMove);
        executer.executeMove(map, info);
        assertEquals(281, map.getTerritory("Gross Hall").getTroopNumUnits("level6"));
        assertEquals(291, map.getTerritory("Gross Hall").getTroopNumUnits("level5"));
        assertEquals(19, map.getTerritory("LSRC").getTroopNumUnits("level6"));
        assertEquals(109, map.getTerritory("LSRC").getTroopNumUnits("level5"));
        assertEquals(-40, map.getPlayerInfo("Blue player").getResTotals().get("food"));
        assertEquals(100, map.getPlayerInfo("Blue player").getResTotals().get("tech"));
    }

    @Test
    public void test_find_highest_level_troop() {
        ActionExecuter executer = new ActionExecuter(10);
        HashMap<String, Integer> defenderUnits = new HashMap<>();
        defenderUnits.put("level6", 0);
        defenderUnits.put("level3", 3);
        defenderUnits.put("level0", 5);
        assertEquals("level3", executer.findHighestLevelTroop(defenderUnits));
    }

    @Test
    public void test_find_lowest_level_troop() {
        ActionExecuter executer = new ActionExecuter(10);
        HashMap<String, Integer> attackerUnits = new HashMap<>();
        attackerUnits.put("level6", 5);
        attackerUnits.put("level3", 3);
        attackerUnits.put("level0", 0);
        assertEquals("level3", executer.findLowestLevelTroop(attackerUnits));
    }

    @Test
    public void test_v2_execute_attack1() {
        WorldMap map = setupV2Map();
        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 30);
        numUnits1.put("level5", 20);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level3", 10);
        map.getTerritory("Fuqua").trySetNumUnits(numUnits2);

        // create order
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals("Green player", map.getTerritory("Fuqua").getOwnerName());
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 =
                af.createAttackActionInfo("Blue player", "Gross Hall", "Fuqua", numUnits1);
        ActionInfo info2 =
                af.createAttackActionInfo("Green player", "Fuqua", "Gross Hall", numUnits2);

        // execute and check
        ActionExecuter executer = new ActionExecuter();
        executer.executePreAttack(map, info1);
        executer.executePreAttack(map, info2);
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals(0, map.getTerritory("Gross Hall").getTroopNumUnits("level6"));
        assertEquals(0, map.getTerritory("Gross Hall").getTroopNumUnits("level5"));
        assertEquals("Green player", map.getTerritory("Fuqua").getOwnerName());
        assertEquals(0, map.getTerritory("Fuqua").getTroopNumUnits("level3"));
        executer.executeAttack(map, info1);
        executer.executeAttack(map, info2);
        assertEquals("Blue player", map.getTerritory("Fuqua").getOwnerName());
        assertEquals(30, map.getTerritory("Fuqua").getTroopNumUnits("level6"));
        assertEquals(20, map.getTerritory("Fuqua").getTroopNumUnits("level5"));
        assertEquals("Green player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals(10, map.getTerritory("Gross Hall").getTroopNumUnits("level3"));
    }

    @Test
    public void test_v2_execute_attack2() {
        // Attacker wins
        WorldMap map = setupV2Map();

        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 30);
        numUnits1.put("level5", 20);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level3", 10);
        map.getTerritory("Fuqua").trySetNumUnits(numUnits2);
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals("Green player", map.getTerritory("Fuqua").getOwnerName());

        // create order
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 =
                af.createAttackActionInfo("Blue player", "Gross Hall", "Fuqua", numUnits1);

        // execute and check
        ActionExecuter executer = new ActionExecuter();
        executer.executePreAttack(map, info1);
        executer.executeAttack(map, info1);
        assertEquals("Blue player", map.getTerritory("Fuqua").getOwnerName());
        assertEquals(30, map.getTerritory("Fuqua").getTroopNumUnits("level6"));
        assertEquals(17, map.getTerritory("Fuqua").getTroopNumUnits("level5"));
    }

    @Test
    public void test_v2_execute_attack3() {
        // Defender wins
        WorldMap map = setupV2Map();

        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 30);
        numUnits1.put("level5", 20);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level3", 10);
        map.getTerritory("Fuqua").trySetNumUnits(numUnits2);
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals("Green player", map.getTerritory("Fuqua").getOwnerName());

        // create order
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info2 =
                af.createAttackActionInfo("Green player", "Fuqua", "Gross Hall", numUnits2);
        ActionExecuter executer = new ActionExecuter();
        executer.executePreAttack(map, info2);
        executer.executeAttack(map, info2);
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals(29, map.getTerritory("Gross Hall").getTroopNumUnits("level6"));
        assertEquals(20, map.getTerritory("Gross Hall").getTroopNumUnits("level5"));
        assertEquals(100 - 10, map.getPlayerInfo("Green player").getResTotals().get("food"));
    }

    @Test
    public void test_upgrade_tech() { // Defender wins
        WorldMap map = setupV2Map();
        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 30);
        numUnits1.put("level5", 20);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level3", 10);
        map.getTerritory("Fuqua").trySetNumUnits(numUnits2);
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals("Green player", map.getTerritory("Fuqua").getOwnerName());
        // create action order
        assertEquals(1, map.getPlayerInfo("Green player").getTechLevel());
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createUpgradeTechActionInfo("Green player", 2);
        // execute
        ActionExecuter executer = new ActionExecuter();
        executer.executeUpgradeTech(map, info1);
        assertEquals(2, map.getPlayerInfo("Green player").getTechLevel());
        assertEquals(100, map.getPlayerInfo("Green player").getResTotals().get("food"));
        assertEquals(100 - 50, map.getPlayerInfo("Green player").getResTotals().get("tech"));
    }

    @Test
    public void test_upgrade_unit() {
        WorldMap map = setupV2Map();
        // put units on map
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 30);
        numUnits1.put("level5", 20);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level3", 10);
        map.getTerritory("Fuqua").trySetNumUnits(numUnits2);
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals("Green player", map.getTerritory("Fuqua").getOwnerName());
        // create action order
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 =
                af.createUpgradeUnitActionInfo("Green player", "Fuqua", "level3", "level4", 3);
        // execute
        ActionExecuter executer = new ActionExecuter();
        executer.executeUpgradeUnit(map, info1);
        assertEquals(3, map.getTerritory("Fuqua").getTroopNumUnits("level4"));
        assertEquals(7, map.getTerritory("Fuqua").getTroopNumUnits("level3"));
        assertEquals(100 - 25 * 3, map.getPlayerInfo("Green player").getResTotals().get("tech"));
        assertEquals(100, map.getPlayerInfo("Green player").getResTotals().get("food"));
    }
}
