package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;

public class InitializeControllerHelper {

    public void initializeTerritoryTooltips(WorldMap map, ArrayList<Label> territoryLabelList) {
        StyleMapping mapping = new StyleMapping();
        for (Label territoryLabel : territoryLabelList) {
            String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
            Tooltip tt = new Tooltip();
            tt.setText(getTerritoryTextInfo(map, territoryName));
            territoryLabel.setTooltip(tt);
        }
    }

    public void initializeTerritoryLabelByGroup(WorldMap map, ArrayList<Label> territoryLabelList) {
        StyleMapping mapping = new StyleMapping();
        // set coloring for each territory label
        for (Label territoryLabel : territoryLabelList) {
            String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
            int initGroup = map.inWhichInitGroup(territoryName);
            territoryLabel.getStyleClass().add("territory-group-" + String.valueOf(initGroup));
        }
    }

    public void initializeTerritoryLabelByOwner() {
        // TODO: add this for initialize controllers for action phase.
    }

    private String getTerritoryTextInfo(WorldMap map, String territoryName) {
        Territory territory = map.getTerritory(territoryName);
        String ans =
                "--------------------------\n"
                        + territoryName
                        + "'s Information:\n"
                        + "--------------------------\n";
        String ownerName = territory.getOwnerName();
        if (ownerName == null) {
            ownerName = "No Owner Yet";
        }
        ans = ans + "- Owner Name: " + ownerName + "\n";
        ans = ans + "- Size: " + territory.getSize() + "\n";
        ans = ans + "- Food Production Rate: " + territory.getResProduction().get("food") + "\n";
        ans = ans + "- Tech Production Rate: " + territory.getResProduction().get("tech") + "\n";
        ans =
                ans
                        + "--------------------------\n"
                        + territoryName
                        + "'s Talents:\n"
                        + "--------------------------\n";
        ans = ans + "- Undergrads: " + territory.getTroopNumUnits("level0") + "\n";
        ans = ans + "- Master: " + territory.getTroopNumUnits("level1") + "\n";
        ans = ans + "- PhD: " + territory.getTroopNumUnits("level2") + "\n";
        ans = ans + "- Postdoc: " + territory.getTroopNumUnits("level3") + "\n";
        ans = ans + "- Asst. Prof: " + territory.getTroopNumUnits("level4") + "\n";
        ans = ans + "- Assc. Prof: " + territory.getTroopNumUnits("level5") + "\n";
        ans = ans + "- Professor: " + territory.getTroopNumUnits("level6") + "\n";
        return ans;
    }
}
