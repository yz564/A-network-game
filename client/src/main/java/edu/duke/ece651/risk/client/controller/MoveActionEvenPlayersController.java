package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.shared.ActionCostCalculator;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;

public class MoveActionEvenPlayersController implements Initializable {
  App model;
  String next;
  ObservableList territoryNames = FXCollections.observableArrayList();
  HashSet<KeyCode> numKeys;

  @FXML ChoiceBox sourceTerritoryName;

  @FXML ChoiceBox destTerritoryName;

  @FXML Label errorMessage;

  @FXML ArrayList<Label> labelList;

  @FXML Label playerInfo;

  @FXML ArrayList<Label> numTalentAvailList;

  @FXML ArrayList<TextField> numTalentList;

  @FXML Label foodCost;

  @FXML Label foodAvailable;

  public MoveActionEvenPlayersController(App model) {
    this.model = model;
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

  @Override
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

    setTerritoryNames();

    // set available food amount
    String playerName = model.getPlayer().getName();
    foodAvailable.setText(String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getResTotals().get("food")));
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

  /* Displays the cost of move action dynamically when user types in the number of units that they wish to move.
   */
  @FXML
  public void onTypingNumUnits(KeyEvent ke) throws Exception {
    Object source = ke.getCode();
    if (this.numKeys.contains(source)) {
      ActionInfo moveInfo = getMoveActionInfo();
      ActionCostCalculator calc = new ActionCostCalculator();
      int cost = calc.calculateCost(moveInfo, model.getPlayer().getMap()).get("food");
      foodCost.setText(String.valueOf(cost));
    }
  }

  public void onSelectSource(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof ChoiceBox) {
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

  /* Triggered when player confirms their move action by clicking on Confirm button.
   */
  @FXML
  public void onMove(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      String isValidInput = checkInput(); // make sure all the inputs are valid for the move order.
      if (isValidInput == null) {
        ActionInfo info = getMoveActionInfo();
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

  /* Returns a move ActionInfo object based on fields entered by the user in the view.
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

  /* Triggered when a player hits the cancel button.
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

  /* Ensures all the inputs required to make a valid move are valid and returns a null string.
   * Returns a string with a descriptive error if check fails.
   */
  String checkInput() {
    try {
      for (TextField numTalent : numTalentList) {
        parseIntFromTextField(numTalent.getText());
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

  /* Returns the number of units requested by the user (by typing in the respective TextField boxes) to issue a move order.
   */
  HashMap<String, Integer> getNumUnits() throws IllegalArgumentException {
    HashMap<String, Integer> numUnits = new HashMap<>();
    for (TextField numTalent : numTalentList) {
      int itemNum = numTalentList.indexOf(numTalent);
      numUnits.put("level" + String.valueOf(itemNum), parseIntFromTextField(numTalent.getText()));
    }
    return numUnits;
  }

  /* Returns an integer from text.
   * @param text is the string from which integer is parsed.
   * @param itemNumber is the index of the TextField. There are five TextField items 1, 2, 3, 4 and 5.
   *        itemNumber is used for printing error messages if parsing fails.
   */
  private int parseIntFromTextField(String text) throws IllegalArgumentException {
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
