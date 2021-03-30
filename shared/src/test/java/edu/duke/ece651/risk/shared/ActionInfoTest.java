package edu.duke.ece651.risk.shared;

import org.assertj.core.api.Assert;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class ActionInfoTest {
    @Test
    public void test_default_constructor() {
        ActionInfo info = new ActionInfo("AAA", "move");
        assertEquals("AAA", info.getSrcOwnerName());
        assertEquals("move", info.getActionType());
    }

    @Test
    public void test_territory_action_constructor() {
        HashMap<String, Integer> unitNum = new HashMap<String, Integer>();
        unitNum.put("level0", 3);
        TerritoryActionInfo infoDetail = new TerritoryActionInfo("a", "b", unitNum);
        ActionInfo info = new ActionInfo("Player 1", "move", infoDetail);
        assertEquals("a", info.getTerritoryActionInfo().getSrcName());

        assertNull(info.getUpgradeTechActionInfo());
        assertNull(info.getUpgradeUnitActionInfo());
    }

    @Test
    public void test_upgrade_unit_action_constructor() {
        UpgradeUnitActionInfo infoDetail = new UpgradeUnitActionInfo("1", 3, 5, 10);
        ActionInfo info = new ActionInfo("Player 1", "upgrade unit", infoDetail);
        assertEquals(10, info.getUpgradeUnitActionInfo().getNumToUpgrade());
    }

    @Test
    public void test_upgrade_tech_action_constructor() {
        UpgradeTechActionInfo infoDetail = new UpgradeTechActionInfo(1, 3);
        ActionInfo info = new ActionInfo("Player 1", "upgrade tech", infoDetail);
        assertEquals(3, info.getUpgradeTechActionInfo().getNewTechLevel());
    }
}
