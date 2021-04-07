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
import java.util.*;

public class AttackActionEvenPlayersController implements Initializable {
  App model;
  String next;
  ObservableList territoryNames = FXCollections.observableArrayList();


  @FXML ChoiceBox sourceTerritoryName;

  @FXML ChoiceBox destTerritoryName;

  @FXML Label errorMessage;

  @FXML ArrayList<Label> labelList;

  @FXML Label playerInfo;

  @FXML ArrayList<Label> numTalentAvailList;

  @FXML ArrayList<TextField> numTalentList;

  @FXML Label foodCost;

  @FXML Label foodAvailable;

  /**
   * Constructor that initializes the model.
   * @param model is the backend of the game.
   */
  public AttackActionEvenPlayersController(App model) {
    this.model = model;
    this.next = "selectActionEvenPlayers";
    HashSet<KeyCode> numKeys;
  }

  /**
   * Sets various elements in the view to default values.
   * @param location is the location of the FXML resource.
   * @param resources used to initialize the root object of the view.
   */
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

    setSourceTerritoryNames();
    setDestinationTerritoryNames();

    // set available food amount
    String playerName = model.getPlayer().getName();
    foodAvailable.setText(String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getResTotals().get("food")));
  }

  /**
   * Fills the choice boxes with a list of territories that a player owns.
   * After the choice boxes are filled, the player is able to select the source
   * territory for attacking.
   */
  private void setSourceTerritoryNames() {
    territoryNames.removeAll(territoryNames);
    String playerName = model.getPlayer().getName();
    HashMap<String, Territory> playerTerritories =
        model.getPlayer().getMap().getPlayerTerritories(playerName);
    for (String territoryName : playerTerritories.keySet()) {
      territoryNames.add(territoryName);
    }
    sourceTerritoryName.getItems().addAll(territoryNames);
  }

  /**
   * Fills the choice boxes with a list of all the territories present on the map.
   * After the choice boxes are filled, the player is able to select the destination
   * territory for attacking.
   */
  private void setDestinationTerritoryNames() {
    territoryNames.removeAll(territoryNames);
    String playerName = model.getPlayer().getName();
    HashMap<String, Territory> playerTerritories =
            model.getPlayer().getMap().getPlayerTerritories(playerName);
    ArrayList<String> allTerritories = model.getPlayer().getMap().getMyTerritories();
    for (String territoryName : allTerritories) {
      // only add a territory to destination if it is not a player territory
      if (!playerTerritories.keySet().contains(territoryName)) {
        territoryNames.add(territoryName);
      }
    }
    destTerritoryName.getItems().addAll(territoryNames);
  }

  /**
   * Displays the cost of attack action dynamically when user types in the number of units that they wish to attack with.
   */
  @FXML
  public void onTypingNumUnits(KeyEvent ke) throws Exception {
    Object source = ke.getCode();
    HashSet<KeyCode> numKeys = new HashSet<>();
    numKeys.add(KeyCode.DIGIT0);
    numKeys.add(KeyCode.DIGIT1);
    numKeys.add(KeyCode.DIGIT2);
    numKeys.add(KeyCode.DIGIT3);
    numKeys.add(KeyCode.DIGIT4);
    numKeys.add(KeyCode.DIGIT5);
    numKeys.add(KeyCode.DIGIT6);
    numKeys.add(KeyCode.DIGIT7);
    numKeys.add(KeyCode.DIGIT8);
    numKeys.add(KeyCode.DIGIT9);
    numKeys.add(KeyCode.BACK_SPACE);
    numKeys.add(KeyCode.TAB);
    if (numKeys.contains(source)) {
      ActionInfo attackInfo = getAttackActionInfo();
      ActionCostCalculator calc = new ActionCostCalculator();
      int cost = calc.calculateCost(attackInfo, model.getPlayer().getMap()).get("food");
      foodCost.setText(String.valueOf(cost));
    }
  }

  /**
   * Sets number of units in the view based on the source territory selected by the player.
   * @param ae is the action event that triggers this function.
   */
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

  /**
   * Triggered when player confirms their attack action by clicking on the Confirm button.
   */
  @FXML
  public void onAttack(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      String isValidInput = checkInput(); // make sure all the inputs are valid for the move order.
      if (isValidInput == null) {
        ActionInfo info = getAttackActionInfo();
        String success = model.getPlayer().tryIssueAttackOrder(info);
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

  /**
   * Triggered when a player hits the cancel button.
   * Player is taken back to the select action window.
   */
  @FXML
  public void onCancel(ActionEvent ae) throws IOException {
    Object source = ae.getSource();
    if (source instanceof Button) {
      loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
    }
    else {
      throw new IllegalArgumentException("Invalid source " + source + " for the cancel upgrade tech level method.");
    }
  }

  /**
   * Returns a attack ActionInfo object based on fields entered by the user in the view.
   */
  private ActionInfo getAttackActionInfo() {
    ActionInfoFactory af = new ActionInfoFactory();
    HashMap<String, Integer> numUnits = getNumUnits();
    ActionInfo info =
            af.createAttackActionInfo(
                    model.getPlayer().getName(),
                    (String) sourceTerritoryName.getValue(),
                    (String) destTerritoryName.getValue(),
                    numUnits);
    return info;
  }

  /**
   * Ensures all the inputs required to make a valid move are valid and returns a null string.
   * Returns a string with a descriptive error if check fails.
   */
  String checkInput() {
    try {
      for (TextField numTalent : numTalentList) {
        parseIntFromTextField(numTalent.getText(), numTalentList.indexOf(numTalent));
      }
      String srcName = (String) sourceTerritoryName.getValue();
      String dstName = (String) destTerritoryName.getValue();
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
      numUnits.put(
          "level" + String.valueOf(itemNum), parseIntFromTextField(numTalent.getText(), itemNum));
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

  /**
   * Loads next phase after a player clicks on a menu item inside a SplitMenuButton.
   * @param window is the source Stage where the user interacted.
   */
  private void loadNextPhase(Stage window) throws IOException {
    Object controller = new ControllerFactory().getController(next, model);
    Stage newWindow = PhaseChanger.switchTo(window, controller, next);
    newWindow.show();
  }
}
