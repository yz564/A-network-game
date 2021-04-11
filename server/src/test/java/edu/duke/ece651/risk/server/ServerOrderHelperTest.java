package edu.duke.ece651.risk.server;

import org.apache.commons.lang3.SerializationUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ObjectIO;
import edu.duke.ece651.risk.shared.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerOrderHelperTest {
    @Test
    public void test_collect_orders() {
        ServerOrderHelper oh = new ServerOrderHelper(9);
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 = new ActionInfo("Green", "attack");
        obj1.attackOrders.add(info1);
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 = new ActionInfo("Blue", "move");
        obj2.moveOrders.add(info2);
        ObjectIO obj3 = new ObjectIO();
        oh.collectOrders(obj1);
        oh.collectOrders(obj2);
        oh.collectOrders(obj3);

        assert (info1 == oh.getAttackOrders().get(0));
        assert (info2 == oh.getGroup1Orders().get(0));
    }

    @Test
    public void test_clear_orders() {
        ServerOrderHelper oh = new ServerOrderHelper();
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 = new ActionInfo("Green", "attack");
        obj1.attackOrders.add(info1);
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 = new ActionInfo("Blue", "move");
        obj2.moveOrders.add(info2);
        ObjectIO obj3 = new ObjectIO();
        oh.collectOrders(obj1);
        oh.collectOrders(obj2);
        oh.collectOrders(obj3);

        oh.clearAllOrders();

        assert (0 == oh.getAttackOrders().size());
        assert (0 == oh.getGroup1Orders().size());
    }

    @Test
    public void test_merge_attack_orders() {
        ActionInfoFactory af = new ActionInfoFactory();
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 300);
        numUnits1.put("level5", 300);
        ActionInfo info1 = af.createAttackActionInfo("A", "Fuqua", "Law", numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level6", 100);
        numUnits2.put("level3", 50);
        ActionInfo info2 = af.createAttackActionInfo("A", "Gross Hall", "Law", numUnits2);
        HashMap<String, Integer> numUnits3 = new HashMap<String, Integer>();
        numUnits3.put("level6", 200);
        numUnits3.put("level3", 150);
        ActionInfo info3 = af.createAttackActionInfo("A", "Fuqua", "Gross Hall", numUnits3);

        ArrayList<ActionInfo> actionOrders = new ArrayList<>();
        actionOrders.add(info1);
        actionOrders.add(info2);
        actionOrders.add(info3);

        ServerOrderHelper oh = new ServerOrderHelper();
        HashMap<String, ActionInfo> mergedOrders = oh.mergeAttackOrders(actionOrders);
        assertEquals(2, mergedOrders.size());
        assertEquals(3, mergedOrders.get("A" + "Law").getNumUnits().size());
        assertEquals(300 + 100, mergedOrders.get("A" + "Law").getNumUnits().get("level6"));
        assertEquals(300, mergedOrders.get("A" + "Law").getNumUnits().get("level5"));
        assertEquals(50, mergedOrders.get("A" + "Law").getNumUnits().get("level3"));
        assertEquals(200, mergedOrders.get("A" + "Gross Hall").getNumUnits().get("level6"));
        assertEquals(150, mergedOrders.get("A" + "Gross Hall").getNumUnits().get("level3"));
    }

    private WorldMap setupV2Map() {
        // create map
        WorldMapFactory mf = new V2MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        map.tryAddPlayerInfo(new PlayerInfo("Green player", 10000, 10000));
        map.tryAddPlayerInfo(new PlayerInfo("Blue player", 10000, 10000));
        map.tryAddPlayerInfo(new PlayerInfo("Red player", 10000, 10000));
        map.getPlayerInfo("Green player").setMultiVizStatus(map.getMyTerritories(), false);
        map.getPlayerInfo("Blue player").setMultiVizStatus(map.getMyTerritories(), false);
        map.getPlayerInfo("Red player").setMultiVizStatus(map.getMyTerritories(), false);
        return map;
    }

    @Test
    public void test_resolve_group1() {
        WorldMap map = setupV2Map();
        ServerOrderHelper oh = new ServerOrderHelper();
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 300);
        numUnits1.put("level5", 300);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level5", 100);
        map.getTerritory("LSRC").trySetNumUnits(numUnits2);

        // execution
        ObjectIO obj1 = new ObjectIO();
        HashMap<String, Integer> toMove = new HashMap<>();
        toMove.put("level6", 19);
        toMove.put("level5", 9);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createMoveActionInfo("Blue player", "Gross Hall", "LSRC", toMove);
        obj1.moveOrders.add(info1);
        oh.collectOrders(obj1);
        WorldMap temp = (WorldMap) SerializationUtils.clone(map);
        assert (oh.rehearseGroup1Orders(temp) == null);
        oh.resolveGroup1Orders(map);
        assertEquals(281, map.getTerritory("Gross Hall").getTroopNumUnits("level6"));
        assertEquals(291, map.getTerritory("Gross Hall").getTroopNumUnits("level5"));
        assertEquals(19, map.getTerritory("LSRC").getTroopNumUnits("level6"));
        assertEquals(109, map.getTerritory("LSRC").getTroopNumUnits("level5"));
        assertEquals(9860, map.getPlayerInfo("Blue player").getResTotals().get("food"));
        assertEquals(10000, map.getPlayerInfo("Blue player").getResTotals().get("tech"));
    }

    @Test
    public void test_resolve_group1_fail() {
        WorldMap map = setupV2Map();
        ServerOrderHelper oh = new ServerOrderHelper();
        HashMap<String, Integer> unitNum1 = new HashMap<>();
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 300);
        numUnits1.put("level5", 300);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level5", 100);
        map.getTerritory("LSRC").trySetNumUnits(numUnits2);

        // execution
        ObjectIO obj1 = new ObjectIO();
        HashMap<String, Integer> toMove = new HashMap<>();
        toMove.put("level6", 19);
        toMove.put("level5", 9);
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createMoveActionInfo("Blue player", "Gross Hall", "LSRC", toMove);
        obj1.moveOrders.add(info1);
        ActionInfo info2 = af.createMoveActionInfo("Blue player", "Gross", "LSRC", toMove);
        obj1.moveOrders.add(info2);
        oh.collectOrders(obj1);
        WorldMap temp = (WorldMap) SerializationUtils.clone(map);
        assertEquals(
                "That action is invalid: source Territory does not exist",
                oh.rehearseGroup1Orders(temp));
        // oh.resolveGroup1Orders(map);
        assertEquals(300, map.getTerritory("Gross Hall").getTroopNumUnits("level6"));
        assertEquals(300, map.getTerritory("Gross Hall").getTroopNumUnits("level5"));
        assertEquals(0, map.getTerritory("LSRC").getTroopNumUnits("level6"));
        assertEquals(100, map.getTerritory("LSRC").getTroopNumUnits("level5"));
        assertEquals(10000, map.getPlayerInfo("Blue player").getResTotals().get("food"));
        assertEquals(10000, map.getPlayerInfo("Blue player").getResTotals().get("tech"));
    }

    @Test
    public void test_resolve_attack1() {
        WorldMap map = setupV2Map();
        ActionInfoFactory af = new ActionInfoFactory();
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
        ActionInfo info1 =
                af.createAttackActionInfo("Blue player", "Gross Hall", "Fuqua", numUnits1);
        ObjectIO obj1 = new ObjectIO();
        obj1.attackOrders.add(info1);
        // execute and check
        ServerOrderHelper oh = new ServerOrderHelper();
        oh.collectOrders(obj1);
        WorldMap temp = (WorldMap) SerializationUtils.clone(map);
        assertNull(oh.rehearseAttackOrders(temp));
        oh.resolveAttackOrders(map);
        assertEquals("Blue player", map.getTerritory("Fuqua").getOwnerName());
        assertEquals(30, map.getTerritory("Fuqua").getTroopNumUnits("level6"));
        assertEquals(17, map.getTerritory("Fuqua").getTroopNumUnits("level5"));
    }

    @Test
    public void test_resolve_attack2() {
        WorldMap map = setupV2Map();
        ActionInfoFactory af = new ActionInfoFactory();
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
        ActionInfo info1 =
                af.createAttackActionInfo("Blue player", "Gross Hall", "Duke Garden", numUnits1);
        ObjectIO obj1 = new ObjectIO();
        obj1.attackOrders.add(info1);
        // execute and check
        ServerOrderHelper oh = new ServerOrderHelper();
        oh.collectOrders(obj1);
        WorldMap temp = (WorldMap) SerializationUtils.clone(map);
        assertEquals(
                "That action is invalid: destination Territory is not adjacent to source Territory",
                oh.tryResolveAllOrders(temp));
    }

    @Test
    public void test_try_resolve_all_orders1() {
        WorldMap map = setupV2Map();
        HashMap<String, Integer> numUnits1 = new HashMap<String, Integer>();
        numUnits1.put("level6", 30);
        numUnits1.put("level5", 20);
        map.getTerritory("Gross Hall").trySetNumUnits(numUnits1);
        HashMap<String, Integer> numUnits2 = new HashMap<String, Integer>();
        numUnits2.put("level3", 10);
        map.getTerritory("Fuqua").trySetNumUnits(numUnits2);
        assertEquals("Blue player", map.getTerritory("Gross Hall").getOwnerName());
        assertEquals("Green player", map.getTerritory("Fuqua").getOwnerName());
        assertEquals(1, map.getPlayerInfo("Green player").getTechLevel());
        // create action order
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info1 = af.createUpgradeTechActionInfo("Green player", 2);
        ObjectIO obj1 = new ObjectIO();
        obj1.moveOrders.add(info1);
        ServerOrderHelper oh = new ServerOrderHelper();
        oh.collectOrders(obj1);
        // execute
        assertNull(oh.tryResolveAllOrders(map));
        assertEquals(2, map.getPlayerInfo("Green player").getTechLevel());
        assertEquals(10000, map.getPlayerInfo("Green player").getResTotals().get("food"));
        assertEquals(10000 - 50, map.getPlayerInfo("Green player").getResTotals().get("tech"));

        // test upgrade tech fail
        oh.clearAllOrders();
        ActionInfo info2 = af.createUpgradeTechActionInfo("Green player", 9);
        obj1.moveOrders.add(info2);
        oh.collectOrders(obj1);
        assertEquals(
                "That action is invalid: new tech level is invalid", oh.tryResolveAllOrders(map));
        assertEquals(2, map.getPlayerInfo("Green player").getTechLevel());
        assertEquals(10000, map.getPlayerInfo("Green player").getResTotals().get("food"));
        assertEquals(10000 - 50, map.getPlayerInfo("Green player").getResTotals().get("tech"));
    }

    @Test
    public void test_try_resolve_all_orders2() {
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
        ObjectIO obj1 = new ObjectIO();
        obj1.moveOrders.add(info1);
        ServerOrderHelper oh = new ServerOrderHelper();
        oh.collectOrders(obj1);
        // execute fail
        assertEquals(
                "That action is invalid: new troop level is invalid", oh.tryResolveAllOrders(map));
        // success
        map.getPlayerInfo("Blue player").setTechLevel(6);
        map.getPlayerInfo("Green player").setTechLevel(6);
        assertNull(oh.tryResolveAllOrders(map));
        assertEquals(3, map.getTerritory("Fuqua").getTroopNumUnits("level4"));
        assertEquals(7, map.getTerritory("Fuqua").getTroopNumUnits("level3"));
        assertEquals(10000 - 25 * 3, map.getPlayerInfo("Green player").getResTotals().get("tech"));
        assertEquals(10000, map.getPlayerInfo("Green player").getResTotals().get("food"));
    }

    @Test
    public void test_send_credit_to_players() {
        WorldMap map = setupV2Map();
        ServerOrderHelper oh = new ServerOrderHelper();
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Green player");
        playerNames.add("Blue player");
        playerNames.add("Red player");
        oh.sendCreditToPlayers(map, playerNames);
        assertEquals(1, map.getTerritory("Fuqua").getTroopNumUnits("level0"));
        assertEquals(1, map.getTerritory("Gross Hall").getTroopNumUnits("level0"));
        assertEquals(10050, map.getPlayerInfo("Green player").getResTotals().get("food"));
        assertEquals(10020, map.getPlayerInfo("Green player").getResTotals().get("tech"));
        assertEquals(10050, map.getPlayerInfo("Blue player").getResTotals().get("food"));
        assertEquals(10020, map.getPlayerInfo("Blue player").getResTotals().get("tech"));
        assertEquals(10050, map.getPlayerInfo("Red player").getResTotals().get("food"));
        assertEquals(10020, map.getPlayerInfo("Red player").getResTotals().get("tech"));
    }

    @Test
    public void test_update_viz_status() {
        WorldMap map = setupV2Map();
        ServerOrderHelper oh = new ServerOrderHelper();
        oh.updateVizStatus(map, "Green player");
        assertTrue(map.getPlayerInfo("Green player").getOneVizStatus("Fuqua"));
        assertTrue(map.getPlayerInfo("Green player").getOneVizStatus("Gross Hall"));
        assertFalse(map.getPlayerInfo("Green player").getOneVizStatus("Duke Clinics"));

        map.getTerritory("Gross Hall").setCloakingTurns(1);
        map.getTerritory("Duke Clinics").tryAddSpyTroopUnits("Green player", 1);
        oh.updateVizStatus(map, "Green player");
        assertFalse(map.getPlayerInfo("Green player").getOneVizStatus("Gross Hall"));
        assertTrue(map.getPlayerInfo("Green player").getOneVizStatus("Duke Clinics"));
    }

    @Test
    public void test_update_multi_viz_status() {
        WorldMap map = setupV2Map();
        ServerOrderHelper oh = new ServerOrderHelper();
        ArrayList<String> playerNames = new ArrayList<>();
        playerNames.add("Green player");
        oh.updateMultiVizStatus(map, playerNames);
        assertTrue(map.getPlayerInfo("Green player").getOneVizStatus("Fuqua"));
        assertTrue(map.getPlayerInfo("Green player").getOneVizStatus("Gross Hall"));
        assertFalse(map.getPlayerInfo("Green player").getOneVizStatus("Duke Clinics"));
    }
}
