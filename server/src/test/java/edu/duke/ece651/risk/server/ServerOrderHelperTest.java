package edu.duke.ece651.risk.server;

import org.junit.jupiter.api.Test;

import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ObjectIO;
import edu.duke.ece651.risk.shared.*;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

public class ServerOrderHelperTest {
    @Test
    public void test_collect_orders() {
        ServerOrderHelper oh = new ServerOrderHelper(9);
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 = new ActionInfo("Green");
        obj1.attackOrders.add(info1);
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 = new ActionInfo("Blue");
        obj2.moveOrders.add(info2);
        ObjectIO obj3 = new ObjectIO();
        oh.collectOrders(obj1);
        oh.collectOrders(obj2);
        oh.collectOrders(obj3);

        assert (info1 == oh.getAttackOrders().get(0));
        assert (info2 == oh.getMoveOrders().get(0));
    }

    @Test
    public void test_clear_orders() {
        ServerOrderHelper oh = new ServerOrderHelper();
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 = new ActionInfo("Green");
        obj1.attackOrders.add(info1);
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 = new ActionInfo("Blue");
        obj2.moveOrders.add(info2);
        ObjectIO obj3 = new ObjectIO();
        oh.collectOrders(obj1);
        oh.collectOrders(obj2);
        oh.collectOrders(obj3);

        oh.clearAllOrders();

        assert (0 == oh.getAttackOrders().size());
        assert (0 == oh.getMoveOrders().size());
    }

    @Test
    public void test_resolve_move() {
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

        ServerOrderHelper oh = new ServerOrderHelper();
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 = new ActionInfo("Green player", "Western Dothraki Sea", "Braavosian Coastlands", 19);
        obj1.moveOrders.add(info1);
        oh.collectOrders(obj1);
        assert (oh.tryResolveMoveOrders(map) == null);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals(119, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));

        oh.clearAllOrders();
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 = new ActionInfo("Green player", "Braavosian Coastlands", "Western Dothraki Sea", 19);
        obj2.moveOrders.add(info2);
        oh.collectOrders(obj2);
        assert (oh.tryResolveMoveOrders(map) == null);
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));
    }

    @Test
    public void test_resolve_move_fail() {
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

        ServerOrderHelper oh = new ServerOrderHelper();
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 = new ActionInfo("Green player", "Western Dothraki Sea", "Braavosian Coastlands", 19);
        obj1.moveOrders.add(info1);
        oh.collectOrders(obj1);
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 = new ActionInfo("Green player", "B", "Western Dothraki Sea", 20);
        obj2.moveOrders.add(info2);
        oh.collectOrders(obj2);
        assertEquals("That action is invalid: source Territory does not exist", oh.tryResolveMoveOrders(map));
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("level0"));
    }

    @Test
    public void test_resolve_attack() {
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");

        // resolve fail
        ServerOrderHelper oh = new ServerOrderHelper();
        HashMap<String, Integer> toAdd1 = new HashMap<>();
        toAdd1.put("level0", 300);
        map.getTerritory("Western Dothraki Sea").setNumUnits(toAdd1);
        HashMap<String, Integer> toAdd2 = new HashMap<>();
        toAdd2.put("level0", 100);
        map.getTerritory("Myr").setNumUnits(toAdd2);
        ActionInfo info1 = new ActionInfo("Green player", "Western Dothraki Sea", "Myr", 300);
        ActionInfo info2 = new ActionInfo("Blue player", "Myr", "Western Dothraki Sea", 100);
        ObjectIO obj1 = new ObjectIO();
        obj1.attackOrders.add(info1);
        obj1.attackOrders.add(info2);
        oh.collectOrders(obj1);
        assertEquals("That action is invalid: destination Territory is not adjacent to source Territory",
                oh.tryResolveAttackOrders(map)); // try resolve
        assertEquals(100, map.getTerritory("Myr").getTroopNumUnits("level0"));
        assertEquals("Blue player", map.getTerritory("Myr").getOwnerName());
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("level0"));
        assertEquals("Green player", map.getTerritory("Western Dothraki Sea").getOwnerName());

        // resolve success
        oh.clearAllOrders();
        HashMap<String, Integer> toAdd3 = new HashMap<>();
        toAdd3.put("level0", 300);
        map.getTerritory("Hills of Horvos").setNumUnits(toAdd3);
        HashMap<String, Integer> toAdd4 = new HashMap<>();
        toAdd4.put("level0", 3);
        map.getTerritory("Lower Rnoyne").setNumUnits(toAdd4);
        ActionInfo info3 = new ActionInfo("Green player", "Hills of Horvos", "Lower Rnoyne", 300);
        ObjectIO obj2 = new ObjectIO();
        obj2.attackOrders.add(info3);
        oh.collectOrders(obj2);
        assert (oh.tryResolveAttackOrders(map) == null);
        assertEquals("Green player", map.getTerritory("Hills of Horvos").getOwnerName());
        assertEquals(0, map.getTerritory("Hills of Horvos").getTroopNumUnits("level0"));
        assertEquals("Green player", map.getTerritory("Lower Rnoyne").getOwnerName());
        assertEquals(298, map.getTerritory("Lower Rnoyne").getTroopNumUnits("level0"));
    }
}
