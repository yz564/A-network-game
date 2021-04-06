package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.controller.InitializeControllerHelper;
import edu.duke.ece651.risk.shared.PlayerInfo;
import edu.duke.ece651.risk.shared.V2MapFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import edu.duke.ece651.risk.shared.WorldMapFactory;
import javafx.scene.control.Label;
import org.junit.jupiter.api.Test;

public class InitializeControllerHelperTest {
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
        return map;
    }

    @Test
    public void test_getPlayerInfo() {
        WorldMap map = setupV2Map();
        InitializeControllerHelper helper = new InitializeControllerHelper();
    }
}
