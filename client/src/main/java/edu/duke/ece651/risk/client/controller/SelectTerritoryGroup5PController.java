package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectTerritoryGroup5PController {
    App model;
    String next;

    @FXML
    Label selectTerritory5pErrorMessage;

    /* Simple constructor that initializes the model for the controller.
     */
    public SelectTerritoryGroup5PController(App model) {
        this.model = model;
        this.next = "test";
    }

    /* Registers group one with the player.
     */
    @FXML
    public void onSelectingGroupOne(ActionEvent ae) throws Exception {
        mapTerritoryWithUser(ae, 1);
    }

    /* Registers group two with the player.
     */
    @FXML
    public void onSelectingGroupTwo(ActionEvent ae) throws Exception {
        mapTerritoryWithUser(ae, 2);
    }

    /* Registers group three with the player.
     */
    @FXML
    public void onSelectingGroupThree(ActionEvent ae) throws Exception {
        mapTerritoryWithUser(ae, 3);
    }

    /* Registers group four with the player.
     */
    @FXML
    public void onSelectingGroupFour(ActionEvent ae) throws Exception {
        mapTerritoryWithUser(ae, 4);
    }

    /* Registers group five with the player.
     */
    @FXML
    public void onSelectingGroupFive(ActionEvent ae) throws Exception {
        mapTerritoryWithUser(ae, 5);
    }

    /* Registers a territory with the player. Or shows an error message on the
     * screen if the group is already registered with a different player.
     * @param ae is the action event that triggers this function.
     */
    private void mapTerritoryWithUser(ActionEvent ae, int territoryGroup) throws Exception {
        Object source = ae.getSource();
        if (source instanceof MenuItem) {
            String assignGroup = model.getPlayer().tryInitialization(String.valueOf(territoryGroup));
            if (assignGroup != null) {
                selectTerritory5pErrorMessage.setText("Territory group already taken by another player. Try choosing a different group.");
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
