package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

/* Class responsible for registering territory group for a player.
 */
public class SelectTerritoryGroup2PController {
    App model;
    String next;

    @FXML
    Label selectTerritory2pErrorMessage;

    /* Simple constructor that initializes the model for the controller.
     */
    public SelectTerritoryGroup2PController(App model) {
        this.model = model;
        this.next = "test";
    }

    /* Registers group one with the player.
     */
    @FXML
    public void onSelectingGroupOne(ActionEvent ae) throws Exception {
        assignTerritoryToUser(ae, 1);
    }

    /* Registers group two with the player.
     */
    @FXML
    public void onSelectingGroupTwo(ActionEvent ae) throws Exception {
        assignTerritoryToUser(ae, 2);
    }

    /* Assigns a territory to a player. Or shows an error message on the
     * screen if the group is already assigned to a different player.
     * @param ae is the action event that triggers this function.
     */
    private void assignTerritoryToUser(ActionEvent ae, int territoryGroup) throws Exception {
        Object source = ae.getSource();
        if (source instanceof MenuItem) {
            String assignGroup = model.getPlayer().tryInitialization(String.valueOf(territoryGroup));
            if (assignGroup != null) {
                selectTerritory2pErrorMessage.setText("Territory group already taken by another player. Try choosing a different group.");
            } else {
                loadNextPhase(ae);
            }
        }
        else {
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
}
