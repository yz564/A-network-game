package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.shared.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MoveActionController extends Controller implements Initializable, ErrorHandlingController {
  ObservableList territoryNames = FXCollections.observableArrayList();
  HashSet<KeyCode> numKeys;
  WorldMap map;

  @FXML ImageView mapImageView;

  @FXML ChoiceBox sourceTerritoryName;

  @FXML ChoiceBox destTerritoryName;

  @FXML Label errorMessage;

  @FXML ArrayList<ToggleButton> labelList;

  @FXML Label playerInfo;

  @FXML ArrayList<Label> numTalentAvailList;

  @FXML ArrayList<TextField> numTalentList;

  @FXML Label foodCost;

  @FXML Label foodAvailable;

  /**
   * Constructor that initializes the model.
   * @param model is the backend of the game.
   */
  public MoveActionController(App model) {
    super(model);
    this.next = "selectAction";

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
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // get map from client App
    this.map = model.getPlayer().getMap();
    InitializeControllerHelper helper = new InitializeControllerHelper();
    // set map image according to number of players
    helper.initializeMap(map, mapImageView);
    // set coloring for each territory label
    helper.initializeTerritoryLabelByOwner(model.getPlayer().getMap(), labelList);
    // set tooltip for each territory label
    helper.initializeTerritoryTooltips(model.getPlayer().getMap(), labelList);
    // set tooltip for player info
    helper.initializePlayerInfoTooltip(
        model.getPlayer().getMap(), model.getPlayer().getName(), playerInfo);
    // set coloring for player info
    helper.initializeTerritoryPlayerInfoColor(model, playerInfo);

    setTerritoryNames();

    // set available food amount
    String playerName = model.getPlayer().getName();
    foodAvailable.setText(String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getResTotals().get("food")));
  }

  /**
   * Fills the choice boxes with a list of territories that a player owns.
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

  /**
   * Displays the cost of move action dynamically when user types in the number of units that they wish to move.
   */
  @FXML
  public void onTypingNumUnits(KeyEvent ke) throws Exception {
    Object source = ke.getCode();
    if (this.numKeys.contains(source)) {
      clearErrorMessage();
      ActionInfo moveInfo = getMoveActionInfo();
      ActionCostCalculator calc = new ActionCostCalculator();
      int cost = calc.calculateCost(moveInfo, model.getPlayer().getMap()).get("food");
      String isValid = checkInput();
      if (isValid != null) {
        setErrorMessage(isValid);
        foodCost.setText("0");
      }
      else {
        foodCost.setText(String.valueOf(cost));
      }
    }
  }

  /**
   * Sets number of units in the view based on the source territory selected by the player.
   * @param ae is the action event that triggers this function.
   */
  public void onSelectSource(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof ChoiceBox) {
      clearErrorMessage();
      String srcTerritory = (String) sourceTerritoryName.getValue();
      HashMap<String, Integer> allNumUnits =
          model.getPlayer().getMap().getTerritory(srcTerritory).getAllNumUnits();
      for (Label numUnitLabel : numTalentAvailList) {
        numUnitLabel.setText(String.valueOf(allNumUnits.get(numUnitLabel.getId())));
      }
    } else {
      throw new IllegalArgumentException("Invalid ActionEvent source " + source);
    }
  }

  /**
   * Triggered when player confirms their move action by clicking on Confirm button.
   */
  @FXML
  public void onMove(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      clearErrorMessage();
      String isValidInput = checkInput(); // make sure all the inputs are valid for the move order.
      if (isValidInput == null) {
        ActionInfo info = getMoveActionInfo();
        String success = model.getPlayer().tryIssueMoveOrder(info);
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
   * Returns a move ActionInfo object based on fields entered by the user in the view.
   */
  private ActionInfo getMoveActionInfo() {
    ActionInfoFactory af = new ActionInfoFactory();
    HashMap<String, Integer> numUnits = getNumUnits();
    ActionInfo info =
        af.createMoveActionInfo(
            model.getPlayer().getName(),
            (String) sourceTerritoryName.getValue(),
            (String) destTerritoryName.getValue(),
            numUnits);
    return info;
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
   * Ensures all the inputs required to make a valid move are valid and returns a null string.
   * Returns a string with a descriptive error if check fails.
   */
  String checkInput() {
    try {
      for (TextField numTalent : numTalentList) {
        if (parseIntFromTextField(numTalent.getText(), numTalentList.indexOf(numTalent)) < 0) {
          throw new IllegalArgumentException("Please enter a non-negative number in box " + numTalentList.indexOf(numTalent) + 1);
        }
      }
      sourceTerritoryName.getValue();
      destTerritoryName.getValue();
    } catch (IllegalArgumentException iae) {
      return iae.getMessage();
    } catch (NullPointerException npe) {
      return "Source and/or destination are empty.";
    }
    return null;
  }

  /**
   * Returns the number of units requested by the user (by typing in the respective TextField boxes) to issue a move order.
   */
  HashMap<String, Integer> getNumUnits() throws IllegalArgumentException {
    HashMap<String, Integer> numUnits = new HashMap<>();
    for (TextField numTalent : numTalentList) {
      int itemNum = numTalentList.indexOf(numTalent);
      numUnits.put("level" + String.valueOf(itemNum), parseIntFromTextField(numTalent.getText(), itemNum));
    }
    return numUnits;
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