package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ActionInfoFactory;
import edu.duke.ece651.risk.shared.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MoveActionOddPlayersController implements Initializable {
    App model;
    String next;
    ObservableList territoryNames = FXCollections.observableArrayList();

    @FXML ChoiceBox sourceTerritoryName;

    @FXML ChoiceBox destTerritoryName;

    @FXML Label territoryGroupName;

    @FXML Label errorMessage;

    // number of talents to move by type of talents
    @FXML TextField numTalent1; // level0

    @FXML TextField numTalent2; // level1

    @FXML TextField numTalent3;

    @FXML TextField numTalent4;

    @FXML TextField numTalent5;

    @FXML TextField numTalent6;

    @FXML TextField numTalent7; // level6

    @FXML ArrayList<Label> labelList;

    public MoveActionOddPlayersController(App model) {
        this.model = model;
        this.next = "selectActionOddPlayers";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set coloring for each territory label
        helper.initializeTerritoryLabelByOwner(model.getPlayer().getMap(), labelList);
        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(model.getPlayer().getMap(), labelList);

        helper.initializeTerritoryGroupLabelColor(model, territoryGroupName);
        setTerritoryNames();
        territoryGroupName.setText("Move");
        // territoryGroupName.setText(model.getPlayer().getTerritoryGroupSelected());
        numTalent1.setText("0");
        numTalent2.setText("0");
        numTalent3.setText("0");
        numTalent4.setText("0");
        numTalent5.setText("0");
        numTalent6.setText("0");
        numTalent7.setText("0");
    }

    /* Fills the choice boxes with a list of territories that a player owns.
     * After the choice boxes are filled, the player is able to select the source and destination
     * territories for moving talents.
     */
    private void setTerritoryNames() {
        territoryNames.removeAll(territoryNames);
        String playerName = model.getPlayer().getName();
        HashMap<String, Territory> playerTerritories =
                model.getPlayer().getMap().getPlayerTerritories(playerName);
        for (String territoryName : playerTerritories.keySet()) {
            territoryNames.add(territoryName);
        }
        sourceTerritoryName.getItems().addAll(territoryNames);
        destTerritoryName.getItems().addAll(territoryNames);
    }

    public void onTypingNumUnits(KeyEvent ke) throws Exception {}

    public void onMove(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            String isValidInput =
                    checkInput(); // make sure all the inputs are valid for the move order.
            if (isValidInput == null) {
                ActionInfoFactory af = new ActionInfoFactory();
                HashMap<String, Integer> numUnits = getNumUnits();
                ActionInfo info =
                        af.createMoveActionInfo(
                                model.getPlayer().getName(),
                                (String) sourceTerritoryName.getValue(),
                                (String) destTerritoryName.getValue(),
                                numUnits);
                String success = model.getPlayer().tryIssueMoveOrder(info);
                if (success != null) {
                    errorMessage.setText(success);
                } else {
                    loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
                }
            } else {
                errorMessage.setText(isValidInput);
            }
        } else {
            throw new IllegalArgumentException("Invalid ActionEvent source " + source);
        }
    }

    /* Ensures all the inputs required to make a valid move are valid and returns a null string.
     * Returns a string with a descriptive error if check fails.
     */
    String checkInput() {
        try {
            parseIntFromTextField(numTalent1.getText(), 1);
            parseIntFromTextField(numTalent2.getText(), 2);
            parseIntFromTextField(numTalent3.getText(), 3);
            parseIntFromTextField(numTalent4.getText(), 4);
            parseIntFromTextField(numTalent5.getText(), 5);
            parseIntFromTextField(numTalent6.getText(), 6);
            parseIntFromTextField(numTalent7.getText(), 7);
            sourceTerritoryName.getValue();
            destTerritoryName.getValue();
        } catch (IllegalArgumentException iae) {
            return iae.getMessage();
        } catch (NullPointerException npe) {
            return "Source and/or destination are empty.";
        }
        return null;
    }

    /* Returns the number of units requested by the user (by typing in the respective TextField boxes) to issue a move order.
     */
    HashMap<String, Integer> getNumUnits() throws IllegalArgumentException {
        HashMap<String, Integer> numUnits = new HashMap<>();
        numUnits.put("level0", parseIntFromTextField(numTalent1.getText(), 1));
        numUnits.put("level1", parseIntFromTextField(numTalent2.getText(), 2));
        numUnits.put("level2", parseIntFromTextField(numTalent3.getText(), 3));
        numUnits.put("level3", parseIntFromTextField(numTalent4.getText(), 4));
        numUnits.put("level4", parseIntFromTextField(numTalent5.getText(), 5));
        numUnits.put("level5", parseIntFromTextField(numTalent6.getText(), 6));
        numUnits.put("level6", parseIntFromTextField(numTalent6.getText(), 7));
        return numUnits;
    }

    /* Returns an integer from text.
     * @param text is the string from which integer is parsed.
     * @param itemNumber is the index of the TextField. There are five TextField items 1, 2, 3, 4 and 5.
     *        itemNumber is used for printing error messages if parsing fails.
     */
    private int parseIntFromTextField(String text, int itemNumber) throws IllegalArgumentException {
        int parsedInt = 0;
        try {
            parsedInt = Integer.parseInt(text);
        } catch (Exception e) {
            throw new IllegalArgumentException("Integer cannot be parsed from " + text);
        }
        return parsedInt;
    }

    /* Loads next phase after a player clicks on a menu item inside a SplitMenuButton.
     * @param window is the source Stage where the user interacted.
     */
    private void loadNextPhase(Stage window) throws IOException {
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }
}
