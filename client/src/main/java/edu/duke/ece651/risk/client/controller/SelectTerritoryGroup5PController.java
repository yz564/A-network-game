package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectTerritoryGroup5PController implements Initializable {
    App model;
    String next;
    WorldMap map;

    @FXML
    Label selectTerritory5pErrorLabel;
    @FXML
    ArrayList<Label> labelList;

    /* Simple constructor that initializes the model for the controller.
     */
    public SelectTerritoryGroup5PController(App model) {
        this.model = model;
        this.next = "test";
    }

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        for (Label territoryLabel: labelList) {
            StyleMapping mapping = new StyleMapping();
            String territoryName = mapping.getTerritoryLabelId(territoryLabel.getId());
            map = model.getPlayer().getMap();
            int initGroup = map.inWhichInitGroup(territoryName);
            territoryLabel.getStyleClass().add("territory-group-" + String.valueOf(initGroup));
        }
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
        if (source instanceof Button) {
            Boolean success = model.getPlayer().tryInitialization(String.valueOf(territoryGroup));
            if (!success) {
                selectTerritory5pErrorLabel.setText("Territory group is already taken by another player.\nTry choosing a different group.");
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
