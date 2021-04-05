package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/* Class responsible for registering territory group for a player.
 */
public class SelectTerritoryGroup2PController implements Initializable {
    App model;
    String next;
    WorldMap map;

    @FXML Label selectTerritory2pErrorLabel;
    @FXML ArrayList<Label> labelList;

    /* Simple constructor that initializes the model for the controller.
     */
    public SelectTerritoryGroup2PController(App model) {
        this.model = model;
        this.next = "allocateTalents2p";
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        StyleMapping mapping = new StyleMapping();
        // set coloring for each territory label
        for (Label territoryLabel : labelList) {
            String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
            map = model.getPlayer().getMap();
            int initGroup = map.inWhichInitGroup(territoryName);
            territoryLabel.getStyleClass().add("territory-group-" + String.valueOf(initGroup));
        }
        // set tooltip for each territory label
        for (Label territoryLabel : labelList) {
            String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
            Tooltip tt = new Tooltip();
            tt.setText(getTerritoryTextInfo(territoryName));
            territoryLabel.setTooltip(tt);
        }
    }

    /* Registers group one with the player.
     */
    @FXML
    public void onSelectingGroupOne(ActionEvent ae) throws Exception {
        assignTerritoryToPlayer(ae, 1);
    }

    /* Registers group two with the player.
     */
    @FXML
    public void onSelectingGroupTwo(ActionEvent ae) throws Exception {
        assignTerritoryToPlayer(ae, 2);
    }

    /* Assigns a territory to a player. Or shows an error message on the
     * screen if the group is already assigned to a different player.
     * @param ae is the action event that triggers this function.
     */
    private void assignTerritoryToPlayer(ActionEvent ae, int territoryGroup) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            Boolean success = model.getPlayer().tryInitialization(String.valueOf(territoryGroup));
            if (!success) {
                selectTerritory2pErrorLabel.setText(
                        "Territory group is already taken by another player.\nTry choosing a different group.");
            } else {
                loadNextPhase(ae);
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /* Loads the next Phase.
     * @param ae is used to compute the parent of the item that interacted
     * with the view that this controller is attached to.
     */
    private void loadNextPhase(ActionEvent ae) throws IOException {
        Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }

    private String getTerritoryTextInfo(String territoryName) {
        Territory territory = model.getPlayer().getMap().getTerritory(territoryName);
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
