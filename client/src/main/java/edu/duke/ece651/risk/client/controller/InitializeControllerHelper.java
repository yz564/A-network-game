package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.PlayerInfo;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;

import java.util.ArrayList;

public class InitializeControllerHelper {

  /**
   * Initializes the Tooltips on each territory label.
   *
   * @param map The game's map for gathering information to display.
   * @param territoryLabelList the territory label ArrayList.
   */
  public void initializeTerritoryTooltips(WorldMap map, ArrayList<Label> territoryLabelList) {
    StyleMapping mapping = new StyleMapping();
    for (Label territoryLabel : territoryLabelList) {
      String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
      Tooltip tt = new Tooltip();
      tt.setText(getTerritoryTextInfo(map, territoryName));
      tt.getStyleClass().add("tooltip-territory");
      territoryLabel.setTooltip(tt);
    }
  }

  /**
   * Initializes the Tooltip on the player info label.
   *
   * @param map The game's map for gathering information to display.
   * @param playerName The player's name to get info about.
   * @param playerInfoLabel the player info Label to set this tooltip with.
   */
  public void initializePlayerInfoTooltip(WorldMap map, String playerName, Label playerInfoLabel) {
    Tooltip tt = new Tooltip();
    tt.setText(getPlayerInfo(map, playerName));
    tt.getStyleClass().add("tooltip-player-info");
    playerInfoLabel.setTooltip(tt);
  }

  /**
   * Initializes the color of each territory label.
   *
   * @param map The game's map for gathering information to display.
   * @param territoryLabelList the territory label ArrayList.
   */
  public void initializeTerritoryLabelByGroup(WorldMap map, ArrayList<Label> territoryLabelList) {
    StyleMapping mapping = new StyleMapping();
    // set coloring for each territory label
    for (Label territoryLabel : territoryLabelList) {
      String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
      int initGroup = map.inWhichInitGroup(territoryName);
      territoryLabel.getStyleClass().add("territory-group-" + String.valueOf(initGroup));
    }
  }

  public void initializeTerritoryLabelByOwner(WorldMap map, ArrayList<Label> territoryLabelList) {
    StyleMapping mapping = new StyleMapping();
    // set coloring for each territory label
    for (Label territoryLabel : territoryLabelList) {
      String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
      int initGroup =
          map.getPlayerInfo(map.getTerritory(territoryName).getOwnerName()).getPlayerId();
      territoryLabel.getStyleClass().add("territory-group-" + String.valueOf(initGroup));
    }
  }

  public void initializeTerritoryGroupLabelColor(App model, Label territoryGroupName) {
    StyleMapping mapping = new StyleMapping();
    int group = model.getPlayer().getMap().getPlayerInfo(model.getPlayer().getName()).getPlayerId();
    String groupName = mapping.territoryGroupColor(group);
    territoryGroupName.getStyleClass().add("territory-group-" + String.valueOf(group));
    territoryGroupName.setText(groupName);
  }

  public void initializeTerritoryPlayerInfoColor(App model, Label playerInfo) {
    int playerId =
        model.getPlayer().getMap().getPlayerInfo(model.getPlayer().getName()).getPlayerId();
    playerInfo.getStyleClass().add("territory-group-" + String.valueOf(playerId));
  }

  /**
   * Returns a String that has text information of the given player.
   *
   * @param map The game's map for gathering information to display.
   * @param playerName The player's name to get info about.
   * @return a String that has text information of the given player.
   */
  private String getPlayerInfo(WorldMap map, String playerName) {
    PlayerInfo info = map.getPlayerInfo(playerName);
    String ans =
        "--------------------------\n"
            + playerName
            + "'s Player Info:\n"
            + "--------------------------\n";
    ans = ans + "- Tech Level: " + info.getTechLevel() + "\n";
    ans = ans + "- Food Resource: " + info.getResTotals().get("food") + "\n";
    ans = ans + "- Tech Resource: " + info.getResTotals().get("tech") + "\n";
    ans = ans + "- Territory Amount: " + map.getPlayerTerritories(playerName).size() + "\n";
    return ans;
  }

  /**
   * Returns a String that has text information of the given territory.
   *
   * @param map The game's map for gathering information to display.
   * @param territoryName The territory's name to get info about.
   * @return a String that has text information of the given territory.
   */
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
