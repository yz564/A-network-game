package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.CharacterButton;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.PlayerInfo;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;

public class InitializeControllerHelper {

    /**
     * Initializes the Tooltips on each territory label.
     *
     * @param map The game's map for gathering information to display.
     * @param territoryLabelList the territory label ArrayList.
     */
    public void initializeTerritoryTooltips(WorldMap map, ArrayList<Label> territoryLabelList) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        for (Label territoryLabel : territoryLabelList) {
            if (numPlayers % 2 == 1 && territoryLabelList.indexOf(territoryLabel) == 15) {
                territoryLabel.setDisable(true);
                territoryLabel.setText("");
                continue;
            }
            String territoryName = mapping.getTerritoryName(territoryLabel.getId());
            Tooltip tt = new Tooltip();
            tt.setText(getTerritoryTextInfo(map, territoryName));
            tt.getStyleClass().add("tooltip-territory");
            tt.setShowDelay(Duration.millis(200));
            tt.setHideDelay(Duration.millis(200));
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
    public void initializePlayerInfoTooltip(
            WorldMap map, String playerName, Label playerInfoLabel) {
        Tooltip tt = new Tooltip();
        tt.setText(getPlayerInfo(map, playerName));
        tt.getStyleClass().add("tooltip-player-info");
        playerInfoLabel.setTooltip(tt);
    }

    public void initializeMap(WorldMap map, ImageView mapImageView) {
        int numPlayers = map.getNumPlayers();
        if (numPlayers % 2 == 0) {
            mapImageView.setImage(new Image("ui/static-images/world-maps/new-map-even.png"));
        } else {
            mapImageView.setImage(new Image("ui/static-images/world-maps/new-map-odd.png"));
        }
    }

    public void initializeTerritoryPlayerInfoColor(App model, Label playerInfo) {
        int playerId =
                model.getPlayer().getMap().getPlayerInfo(model.getPlayer().getName()).getPlayerId();
        playerInfo.getStyleClass().add("territory-group-" + String.valueOf(playerId));
    }

    /**
     * Initializes the color of each territory label.
     *
     * @param map The game's map for gathering information to display.
     * @param territoryLabelList the territory label ArrayList.
     */
    public void initializeTerritoryLabelByGroup(WorldMap map, ArrayList<Label> territoryLabelList) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        // set coloring for each territory label
        for (Label territoryLabel : territoryLabelList) {
            if (numPlayers % 2 == 1 && territoryLabelList.indexOf(territoryLabel) == 15) {
                territoryLabel.setText("");
                territoryLabel.setDisable(true);
                continue;
            }
            String territoryName = mapping.getTerritoryName(territoryLabel.getId());
            int initGroup = map.inWhichInitGroup(territoryName);
            territoryLabel.getStyleClass().add("territory-group-" + String.valueOf(initGroup));
        }
    }

    public void initializeTerritoryLabelByOwner(WorldMap map, ArrayList<Label> territoryLabelList) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        // set coloring for each territory label
        for (Label territoryLabel : territoryLabelList) {
            if (numPlayers % 2 == 1 && territoryLabelList.indexOf(territoryLabel) == 15) {
                territoryLabel.setText("");
                territoryLabel.setDisable(true);
                continue;
            }
            String territoryName = mapping.getTerritoryName(territoryLabel.getId());
            int initGroup =
                    map.getPlayerInfo(map.getTerritory(territoryName).getOwnerName()).getPlayerId();
            territoryLabel.getStyleClass().add("territory-group-" + String.valueOf(initGroup));
        }
    }

    public void initializeSelectedCharacter(
            App model, Circle characterButton, Label characterLabel) {
        int playerId =
                model.getPlayer().getMap().getPlayerInfo(model.getPlayer().getName()).getPlayerId();
        StyleMapping mapping = new StyleMapping();
        CharacterButton leader =
                new CharacterButton(characterButton, characterLabel, mapping, playerId);
        leader.setImage();
        leader.setLabel();
    }

    public void initializeCharacter(
            WorldMap map,
            ArrayList<Circle> characterButtonList,
            ArrayList<Label> characterLabelList) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        for (int i = 0; i < characterButtonList.size(); i++) {
            if (i >= numPlayers) {
                characterButtonList.get(i).setDisable(true);
                characterLabelList.get(i).setDisable(true);
                continue;
            }
            CharacterButton leader =
                    new CharacterButton(
                            characterButtonList.get(i), characterLabelList.get(i), mapping);
            leader.setImage();
            leader.setLabel();
        }
    }

    public void initializeNumUnitsFields(
            App model, ArrayList<Label> territoryLabelList, ArrayList<TextField> numUnitsList) {
        int playerId =
                model.getPlayer().getMap().getPlayerInfo(model.getPlayer().getName()).getPlayerId();
        ArrayList<String> playerTerritories = model.getPlayer().getMap().getInitGroup(playerId);
        StyleMapping mapping = new StyleMapping();
        for (int i = 0; i < territoryLabelList.size(); i++) {
            String labelName = territoryLabelList.get(i).getId();
            String territoryName = mapping.getTerritoryName(labelName);
            if (!playerTerritories.contains(territoryName)) {
                numUnitsList.get(i).setVisible(false);
            }
        }
    }

    public void initializeTerritoryTotalNumUnitsLabels(
            WorldMap map, ArrayList<Label> territoryLabelList, ArrayList<Label> numLabelList){
        StyleMapping mapping = new StyleMapping();
        for (int i = 0; i < territoryLabelList.size(); i++){
            String labelName = territoryLabelList.get(i).getId();
            String territoryName = mapping.getTerritoryName(labelName);
            numLabelList.get(i).setText(String.valueOf(map.getTerritory(territoryName).getTotalNumUnits()));
        }
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
