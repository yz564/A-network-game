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

  @FXML ChoiceBox sourceTerritoryName;

  @FXML ChoiceBox destTerritoryName;

  @FXML Label errorMessage;

  @FXML ArrayList<Label> labelList;

  @FXML Label playerInfo;

  @FXML ArrayList<Label> numTalentAvailList;

  @FXML ArrayList<TextField> numTalentList;

  public MoveActionEvenPlayersController(App model) {
    this.model = model;
    this.next = "selectActionEvenPlayers";
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

  public void onMove(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      String isValidInput = checkInput(); // make sure all the inputs are valid for the move order.
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
