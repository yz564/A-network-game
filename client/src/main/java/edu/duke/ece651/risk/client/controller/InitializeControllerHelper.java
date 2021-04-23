package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.CharacterButton;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.PlayerInfo;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class InitializeControllerHelper {

    /**
     * Initializes the Tooltips on each territory label.
     *
     * @param map The game's map for gathering information to display.
     * @param territoryLabelList the territory label ArrayList.
     */
    public void initializeTerritoryTooltips(
            WorldMap map, ArrayList<ToggleButton> territoryLabelList, String playerName) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        for (ToggleButton territoryLabel : territoryLabelList) {
            if (numPlayers % 2 == 1 && territoryLabelList.indexOf(territoryLabel) == 15) {
                territoryLabel.setDisable(true);
                territoryLabel.setText("");
                continue;
            }
            String territoryName = mapping.getTerritoryName(territoryLabel.getId());
            Tooltip tt = new Tooltip();
            tt.setText(getTerritoryTextInfo(map, territoryName, playerName));
            tt.getStyleClass().add("tooltip-territory");
            tt.setShowDelay(Duration.millis(1000));
            tt.setHideDelay(Duration.millis(200));
            territoryLabel.setTooltip(tt);
        }
    }

    /**
     * Initializes the Tooltip on the player info label.
     *
     * @param map The game's map for gathering information to display.
     * @param playerName The player's name to get info about.
     * @param playerCharacter the player info Label to set this tooltip with.
     */
    public void initializePlayerInfoTooltip(
            WorldMap map, String playerName, Circle playerCharacter) {
        Tooltip tt = new Tooltip();
        tt.setText(getPlayerInfo(map, playerName));
        tt.getStyleClass().add("tooltip-player-info");
        tt.setShowDelay(Duration.millis(500));
        tt.setHideDelay(Duration.millis(200));
        Tooltip.install(playerCharacter, tt);
    }

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
        playerInfo.getStyleClass().add("territory-group-" + playerId);
    }

    /**
     * Initializes the color of each territory label.
     *
     * @param map The game's map for gathering information to display.
     * @param territoryLabelList the territory label ArrayList.
     */
    public void initializeTerritoryLabelByGroup(
            WorldMap map, ArrayList<ToggleButton> territoryLabelList) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        // set coloring for each territory label
        for (ToggleButton territoryLabel : territoryLabelList) {
            if (numPlayers % 2 == 1 && territoryLabelList.indexOf(territoryLabel) == 15) {
                territoryLabel.setText("");
                territoryLabel.setDisable(true);
                continue;
            }
            String territoryName = mapping.getTerritoryName(territoryLabel.getId());
            int initGroup = map.inWhichInitGroup(territoryName);
            territoryLabel.getStyleClass().add("territory-group-disabled-" + initGroup);
        }
    }

    public void initializeTerritoryLabelByOwner(
            WorldMap map, ArrayList<ToggleButton> territoryLabelList) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        // set coloring for each territory label
        for (ToggleButton territoryLabel : territoryLabelList) {
            if (numPlayers % 2 == 1 && territoryLabelList.indexOf(territoryLabel) == 15) {
                territoryLabel.setText("");
                territoryLabel.setDisable(true);
                continue;
            }
            String territoryName = mapping.getTerritoryName(territoryLabel.getId());
            int initGroup =
                    map.getPlayerInfo(map.getTerritory(territoryName).getOwnerName()).getPlayerId();
            territoryLabel.getStyleClass().add("territory-group-" + initGroup);
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
            App model,
            ArrayList<ToggleButton> territoryLabelList,
            ArrayList<TextField> numUnitsList) {
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
            WorldMap map,
            ArrayList<ToggleButton> territoryLabelList,
            ArrayList<Label> numLabelList) {
        int numPlayers = map.getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        for (int i = 0; i < territoryLabelList.size(); i++) {
            if (numPlayers % 2 == 1 && i == 15) {
                numLabelList.get(i).setText("");
                numLabelList.get(i).setDisable(true);
                continue;
            }
            String labelName = territoryLabelList.get(i).getId();
            String territoryName = mapping.getTerritoryName(labelName);
            numLabelList
                    .get(i)
                    .setText(String.valueOf(map.getTerritory(territoryName).getTotalNumUnits()));
        }
    }

    public void initializeTerritoryButtons(App model, ArrayList<ToggleButton> territoryLabelList) {
        String playerName = model.getPlayer().getName();
        PlayerInfo playerInfo = model.getPlayer().getMap().getPlayerInfo(playerName);
        HashMap<String, Boolean> limited = model.getPlayer().getIsLimitedActionUsed();
        int numPlayers = model.getPlayer().getMap().getNumPlayers();
        StyleMapping mapping = new StyleMapping();
        for (ToggleButton territoryButton : territoryLabelList) {
            if (numPlayers % 2 == 1 && territoryLabelList.indexOf(territoryButton) == 15) {
                territoryButton.setText("");
                territoryButton.setDisable(true);
                continue;
            }
            String territoryName = mapping.getTerritoryName(territoryButton.getId());
            Territory territory = model.getPlayer().getMap().getTerritory(territoryName);
            int playerId =
                    model.getPlayer()
                            .getMap()
                            .getPlayerInfo(territory.getOwnerName())
                            .getPlayerId();
            if (territory.getSpyTroopNumUnits(playerName) <= 0 || limited.get("move spy")) {
                if ((territory.isBelongTo(playerName)
                                && !playerInfo.getIsCloakingResearched()
                                && territory.getTotalNumUnits() <= 0)
                        || !territory.isBelongTo(playerName)) {
                    territoryButton
                            .getStyleClass()
                            .removeAll("territory-group-" + String.valueOf(playerId));
                    territoryButton
                            .getStyleClass()
                            .addAll("territory-group-disabled-" + String.valueOf(playerId));
                    territoryButton.setOnAction(null);
                    // territoryButton.setDisable(true);
                }
            }
        }
    }

    public void initializeNumUnit(
            WorldMap map,
            String srcName,
            String destName,
            ArrayList<Label> srcNumList,
            ArrayList<Label> destNumList) {
        Territory src = map.getTerritory(srcName);
        Territory dest = map.getTerritory(destName);
        for (int i = 0; i < srcNumList.size(); i++) {
            srcNumList
                    .get(i)
                    .setText(String.valueOf(src.getTroopNumUnits("level" + i)));
            destNumList
                    .get(i)
                    .setText(String.valueOf(dest.getTroopNumUnits("level" + i)));
        }
    }

    public void initializeSliders(ArrayList<Slider> sliderList, ArrayList<Label> srcNumList) {
        for (int i = 0; i < srcNumList.size(); i++) {
            sliderList.get(i).setMax(Integer.parseInt(srcNumList.get(i).getText()));
        }
    }

    public void initializeTalentRows(
            ArrayList<Label> srcNumList, ArrayList<Label> destNumList, GridPane grid) {
        for (int i = 0; i < srcNumList.size(); i++) {
            int srcNum = Integer.parseInt(srcNumList.get(i).getText());
            int destNum = Integer.parseInt(destNumList.get(i).getText());
            if (srcNum <= 0 && destNum <= 0) {
                int row = i * 2 + 5;
                grid.getRowConstraints().get(row).setMaxHeight(0);
                grid.getRowConstraints().get(row + 1).setMaxHeight(0);
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == row);
                grid.getChildren().removeIf(node -> GridPane.getRowIndex(node) == row + 1);
            }
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
        ans =
                ans
                        + "- Cloaking Researched: "
                        + map.getPlayerInfo(playerName).getIsCloakingResearched()
                        + "\n";
        return ans;
    }

    /**
     * Returns a String that has text information of the given territory.
     *
     * @param map The game's map for gathering information to display.
     * @param territoryName The territory's name to get info about.
     * @return a String that has text information of the given territory.
     */
    private String getTerritoryTextInfo(WorldMap map, String territoryName, String playerName) {
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
        ans = ans + "- Cloaking Turns: " + territory.getCloakingTurns() + "\n";
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
        ans = ans + "- Spy: " + territory.getSpyTroopNumUnits(playerName) + "\n";
        return ans;
    }
}
