package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.*;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MoveSpyActionEvenPlayersController extends Controller implements Initializable, ErrorHandlingController {
    ObservableList sourceTerritoryNames = FXCollections.observableArrayList();
    ObservableList destTerritoryNames = FXCollections.observableArrayList();
    @FXML ArrayList<Label> labelList;
    @FXML ChoiceBox sourceTerritoryName;
    @FXML ChoiceBox destTerritoryName;
    @FXML Label playerInfo;
    @FXML TextField numSpies;
    @FXML Label numSpiesAvailable;
    @FXML Label foodCost;
    @FXML Label foodAvailable;
    @FXML Label errorMessage;

    HashSet<KeyCode> numKeys;

    /**
     * Constructor that initializes the model.
     * @param model is the backend of the game.
     */
    public MoveSpyActionEvenPlayersController(App model) {
        super(model);
        this.next = "selectActionEvenPlayers";

        // keys that trigger dynamic cost calculation for the move order
        this.numKeys = new HashSet<>();
        this.numKeys.add(KeyCode.DIGIT0);
        this.numKeys.add(KeyCode.DIGIT1);
        this.numKeys.add(KeyCode.DIGIT2);
        this.numKeys.add(KeyCode.DIGIT3);
        this.numKeys.add(KeyCode.DIGIT4);
        this.numKeys.add(KeyCode.DIGIT5);
        this.numKeys.add(KeyCode.DIGIT6);
        this.numKeys.add(KeyCode.DIGIT7);
        this.numKeys.add(KeyCode.DIGIT8);
        this.numKeys.add(KeyCode.DIGIT9);
        this.numKeys.add(KeyCode.BACK_SPACE);
        this.numKeys.add(KeyCode.TAB);
    }

    /**
     * Sets various elements in the view to default values.
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    public void initialize(URL location, ResourceBundle resources) {
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set coloring for each territory label
        helper.initializeTerritoryLabelByOwner(model.getPlayer().getMap(), labelList);
        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(model.getPlayer().getMap(), labelList);
        // set tooltip for player info
        helper.initializePlayerInfoTooltip(
                model.getPlayer().getMap(), model.getPlayer().getName(), playerInfo);
        // set coloring for player info
        helper.initializeTerritoryPlayerInfoColor(model, playerInfo);

        setSourceTerritoryNames();
    }

    /**
     * Fills the source territory choice box with a list of territories that has a player spy.
     */
    private void setSourceTerritoryNames() {
        sourceTerritoryNames.removeAll(sourceTerritoryNames);
        WorldMap worldMap = model.getPlayer().getMap();
        String playerName = model.getPlayer().getName();
        ArrayList<String> allTerritories = worldMap.getMyTerritories();
        for (String territoryName: allTerritories) {
            if (worldMap.getTerritory(territoryName).getSpyTroopNumUnits(playerName) > 0) {
                sourceTerritoryNames.add(territoryName);
            }
        }
        sourceTerritoryName.getItems().addAll(sourceTerritoryNames);
    }

    /**
     * Sets the choice box for destination territories where spy could move from the source territory.
     * @param ae is the action event that triggers this function.
     */
    @FXML
    private void onSelectSource(ActionEvent ae) {
        Object source = ae.getSource();
        if (source instanceof ChoiceBox) {
            clearErrorMessage();
            destTerritoryNames.removeAll(destTerritoryNames);
            String srcTerritoryName = (String) sourceTerritoryName.getValue();
            Territory srcTerritory = model.getPlayer().getMap().getTerritory(srcTerritoryName);
            HashMap<String, Territory> neighbors = srcTerritory.getMyNeighbors();
            destTerritoryNames.addAll(neighbors.keySet());
            destTerritoryName.getItems().addAll(destTerritoryNames);
        }
        else {
            throw new IllegalArgumentException("Invalid ActionEvent source " + source);
        }
    }

    /**
     * Triggered when player confirms their move action by clicking on Confirm button.
     */
    @FXML
    public void onMove(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if (source instanceof Button) {
            clearErrorMessage();
            String isValidInput = checkInput(); // make sure all the inputs are valid for the move spy order.
            if (isValidInput == null) {
                ActionInfo info = getMoveSpyActionInfo();
                String success = model.getPlayer().tryIssueMoveSpyOrder(info);
                if (success != null) {
                    setErrorMessage(success);
                } else {
                    loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
                }
            } else {
                setErrorMessage(isValidInput);
            }
        } else {
            throw new IllegalArgumentException("Invalid ActionEvent source " + source);
        }
    }

    /**
     * Triggered when a player hits the cancel button.
     * Player is taken back to the select action window.
     */
    @FXML
    public void onCancel(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if (source instanceof Button) {
            loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for the cancel upgrade tech level method.");
        }
    }

    /**
     * Displays the cost of move spy action dynamically when user types in the number of spies that they wish to move.
     */
    @FXML
    public void onTypingNumUnits(KeyEvent ke) {
        Object source = ke.getCode();
        if (this.numKeys.contains(source)) {
            clearErrorMessage();
            String isValidInput = checkInput();
            if (isValidInput == null) {
                ActionInfo moveSpyInfo = getMoveSpyActionInfo();
                ActionCostCalculator calc = new ActionCostCalculator();
                int cost = calc.calculateCost(moveSpyInfo, model.getPlayer().getMap()).get("food");
                foodCost.setText(String.valueOf(cost));
            }
            else {
                setErrorMessage(isValidInput);
                foodCost.setText("0");
            }
        }
    }

    /**
     * Returns a move spy ActionInfo object based on fields entered by the user in the view.
     */
    private ActionInfo getMoveSpyActionInfo() throws IllegalArgumentException, NullPointerException {
        ActionInfoFactory af = new ActionInfoFactory();
        int numUnits = parseIntFromTextField(numSpies.getText(), 1);
        ActionInfo info =
                af.createMoveSpyActionInfo(
                        model.getPlayer().getName(),
                        (String) sourceTerritoryName.getValue(),
                        (String) destTerritoryName.getValue(),
                        numUnits);
        return info;
    }

    /**
     * Ensures all the inputs required to make a valid spy move are valid and returns a null string.
     * Returns a string with a descriptive error if check fails.
     */
    private String checkInput() {
        try {
            if (parseIntFromTextField(numSpies.getText(), 1) < 0) {
                throw new IllegalArgumentException("Please enter a non-negative number for moving spies.");
            }
            if ((String) sourceTerritoryName.getValue() == null) {
                throw new NullPointerException("Source territory is empty.");
            }
            else if ((String) destTerritoryName.getValue() == null) {
                throw new NullPointerException("Destination territory is empty.");
            }
        } catch (IllegalArgumentException iae) {
            return iae.getMessage();
        } catch (NullPointerException npe) {
            return npe.getMessage();
        } catch (Exception e) {
            return "Invalid input.";
        }
        return null;
    }

    /**
     * Returns an integer from text.
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
    @Override
    public void setErrorMessage(String error) {
        errorMessage.setText(error);
    }

    @Override
    public void clearErrorMessage() {
        setErrorMessage(null);
    }
}
