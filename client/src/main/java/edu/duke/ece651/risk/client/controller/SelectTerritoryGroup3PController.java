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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectTerritoryGroup3PController extends Controller implements Initializable {
    WorldMap map;

    @FXML Label selectTerritory3pErrorLabel;
    @FXML ArrayList<Label> labelList;

    /**
     * Constructor that initializes the model.
     * @param model is the backend of the game.
     */
    public SelectTerritoryGroup3PController(App model) {
        super(model);
        this.next = "allocateTalents3p";
    }

    /**
     * Sets various elements in the view to default values.
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set coloring for each territory label
        helper.initializeTerritoryLabelByGroup(model.getPlayer().getMap(), labelList);
        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(model.getPlayer().getMap(), labelList);
    }

    /**
     * Registers group one with the player.
     */
    @FXML
    public void onSelectingGroupOne(ActionEvent ae) throws Exception {
        assignTerritoryToPlayer(ae, 1);
    }

    /**
     * Registers group two with the player.
     */
    @FXML
    public void onSelectingGroupTwo(ActionEvent ae) throws Exception {
        assignTerritoryToPlayer(ae, 2);
    }

    /**
     * Registers group three with the player.
     */
    @FXML
    public void onSelectingGroupThree(ActionEvent ae) throws Exception {
        assignTerritoryToPlayer(ae, 3);
    }

    /**
     * Registers a territory with the player. Or shows an error message on the
     * screen if the group is already registered with a different player.
     * @param ae is the action event that triggers this function.
     */
    private void assignTerritoryToPlayer(ActionEvent ae, int territoryGroup) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            Boolean success = model.getPlayer().tryInitialization(String.valueOf(territoryGroup));
            if (!success) {
                selectTerritory3pErrorLabel.setText(
                        "Territory group is already taken by another player.\nTry choosing a different group.");
            } else {
                loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
