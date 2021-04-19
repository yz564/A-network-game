package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.controller.InitializeControllerHelper;
import edu.duke.ece651.risk.shared.PlayerInfo;
import edu.duke.ece651.risk.shared.V2MapFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import edu.duke.ece651.risk.shared.WorldMapFactory;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
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
    public void test_initializePlayerInfoTooltip() {
        WorldMap map = setupV2Map();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        helper.initializePlayerInfoTooltip(map, "Green player", new Label());
    }

    @Test
    public void test_initializeTerritoryTooltips() {
        WorldMap map = setupV2Map();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        ToggleButton territoryLabel = new ToggleButton();
        territoryLabel.setId("label0");
        ArrayList<ToggleButton> labels = new ArrayList<>();
        labels.add(territoryLabel);
        helper.initializeTerritoryTooltips(map, labels);
    }

    @Test
    public void test_initializeTerritoryLabelByGroup() {
        WorldMap map = setupV2Map();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        ToggleButton territoryLabel = new ToggleButton();
        territoryLabel.setId("label0");
        ArrayList<ToggleButton> labels = new ArrayList<>();
        labels.add(territoryLabel);
        helper.initializeTerritoryLabelByGroup(map, labels);
    }

    @Test
    public void test_initializeTerritoryLabelByOwner() {
        WorldMap map = setupV2Map();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        ToggleButton territoryLabel = new ToggleButton();
        territoryLabel.setId("label0");
        ArrayList<ToggleButton> labels = new ArrayList<>();
        labels.add(territoryLabel);
        helper.initializeTerritoryLabelByOwner(map, labels);
    }
}
