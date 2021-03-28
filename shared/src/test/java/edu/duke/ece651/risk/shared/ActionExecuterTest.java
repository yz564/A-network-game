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
    map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
    map.getTerritory("Braavosian Coastlands").trySetTroopUnits("Basic", 100);
    ActionExecuter executer = new ActionExecuter();
    ActionInfo info = new ActionInfo("Green player", "Western Dothraki Sea", "Braavosian Coastlands", 19);
    executer.sendTroops(map, info);
    assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
    assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
  }

  @Test
  public void test_execute_move() {
    // create map
    WorldMapFactory mf = new V1MapFactory();
    WorldMap map = mf.makeWorldMap(3);
    map.tryAssignInitOwner(1, "Green player");
    map.tryAssignInitOwner(2, "Blue player");
    map.tryAssignInitOwner(3, "Red player");
    map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
    map.getTerritory("Braavosian Coastlands").trySetTroopUnits("Basic", 100);

    ActionExecuter executer = new ActionExecuter();
    ActionInfo info1 = new ActionInfo("Green player", "Western Dothraki Sea", "Braavosian Coastlands", 19);
    executer.executeMove(map, info1);
    assertEquals(281, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
    assertEquals(119, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));

    ActionInfo info2 = new ActionInfo("Green player", "Braavosian Coastlands", "Western Dothraki Sea", 19);
    executer.executeMove(map, info2);
    assertEquals(300, map.getTerritory("Western Dothraki Sea").getTroopNumUnits("Basic"));
    assertEquals(100, map.getTerritory("Braavosian Coastlands").getTroopNumUnits("Basic"));
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

    map.getTerritory("Western Dothraki Sea").trySetTroopUnits("Basic", 300);
    map.getTerritory("Myr").trySetTroopUnits("Basic", 100);
    ActionInfo info1 = new ActionInfo("Green player", "Western Dothraki Sea", "Myr", 300);
    ActionInfo info2 = new ActionInfo("Blue player", "Myr", "Western Dothraki Sea", 100);
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

    ActionInfo info3 = new ActionInfo("Green player", "Braavosian Coastlands", "Lower Rnoyne", 300);
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

    ActionInfo info4 = new ActionInfo("Green player", "Forest of Qohor", "Lhaxar", 3);
    executer0.sendTroops(map, info4);
    executer0.executeAttack(map, info4);
    assertEquals("Blue player", map.getTerritory("Lhaxar").getOwnerName());
    assertEquals(497, map.getTerritory("Lhaxar").getTroopNumUnits("Basic"));
  }
}
