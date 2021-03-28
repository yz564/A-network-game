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
        assert (info2 == oh.getMoveOrders().get(0));
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
        map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
        map.getTerritory("Braavosian Coastlands").trySetTroopUnits("Basic", 100);

        ServerOrderHelper oh = new ServerOrderHelper();
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 =
                new ActionInfo(
                        "Green player",
                        "move",
                        "Western Dothraki Sea",
                        "Braavosian Coastlands",
                        19);
        obj1.moveOrders.add(info1);
        oh.collectOrders(obj1);
        assert (oh.tryResolveMoveOrders(map) == null);
        assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals(119, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));

        oh.clearAllOrders();
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 =
                new ActionInfo(
                        "Green player",
                        "move",
                        "Braavosian Coastlands",
                        "Western Dothraki Sea",
                        19);
        obj2.moveOrders.add(info2);
        oh.collectOrders(obj2);
        assert (oh.tryResolveMoveOrders(map) == null);
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
    }

    @Test
    public void test_resolve_move_fail() {
        // create map
        WorldMapFactory mf = new V1MapFactory();
        WorldMap map = mf.makeWorldMap(3);
        map.tryAssignInitOwner(1, "Green player");
        map.tryAssignInitOwner(2, "Blue player");
        map.tryAssignInitOwner(3, "Red player");
        map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
        map.getTerritory("Braavosian Coastlands").trySetTroopUnits("Basic", 100);

        ServerOrderHelper oh = new ServerOrderHelper();
        ObjectIO obj1 = new ObjectIO();
        ActionInfo info1 =
                new ActionInfo(
                        "Green player",
                        "move",
                        "Western Dothraki Sea",
                        "Braavosian Coastlands",
                        19);
        obj1.moveOrders.add(info1);
        oh.collectOrders(obj1);
        ObjectIO obj2 = new ObjectIO();
        ActionInfo info2 = new ActionInfo("Green player", "move", "B", "Western Dothraki Sea", 20);
        obj2.moveOrders.add(info2);
        oh.collectOrders(obj2);
        assertEquals(
                "That action is invalid: source Territory does not exist",
                oh.tryResolveMoveOrders(map));
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
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
        map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
        map.getTerritory("Myr").trySetTroopUnits("Basic", 100);
        ActionInfo info1 =
                new ActionInfo("Green player", "attack", "Western Dothraki Sea", "Myr", 300);
        ActionInfo info2 =
                new ActionInfo("Blue player", "attack", "Myr", "Western Dothraki Sea", 100);
        ObjectIO obj1 = new ObjectIO();
        obj1.attackOrders.add(info1);
        obj1.attackOrders.add(info2);
        oh.collectOrders(obj1);
        assertEquals(
                "That action is invalid: destination Territory is not adjacent to source Territory",
                oh.tryResolveAttackOrders(map)); // try resolve
        assertEquals(100, map.getTerritory("Myr").getTroopNumUnits("Basic"));
        assertEquals("Blue player", map.getTerritory("Myr").getOwnerName());
        assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
        assertEquals("Green player", map.getTerritory("Western Dothraki Sea").getOwnerName());

        // resolve success
        oh.clearAllOrders();
        map.getTerritory("Hills of Horvos").trySetTroopUnits("Basic", 300);
        map.getTerritory("Lower Rnoyne").trySetTroopUnits("Basic", 3);
        ActionInfo info3 =
                new ActionInfo("Green player", "attack", "Hills of Horvos", "Lower Rnoyne", 300);
        ObjectIO obj2 = new ObjectIO();
        obj2.attackOrders.add(info3);
        oh.collectOrders(obj2);
        assertNull(oh.tryResolveAttackOrders(map));
        assertEquals("Green player", map.getTerritory("Hills of Horvos").getOwnerName());
        assertEquals(0, map.getTerritory("Hills of Horvos").getTroopNumUnits("Basic"));
        assertEquals("Green player", map.getTerritory("Lower Rnoyne").getOwnerName());
        assertEquals(298, map.getTerritory("Lower Rnoyne").getTroopNumUnits("Basic"));
    }
}
