package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.client.view.StyleMapping;
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

public class UpgradeTalentsActionEvenPlayersController implements Initializable {
  App model;
  String next;
  ObservableList territoryNames;
  ObservableList talentNamesFrom;
  ObservableList talentNamesTo;
  HashMap<String, String> talents;

  @FXML ChoiceBox sourceTerritoryName;

  @FXML Label techLevel;

  @FXML ChoiceBox sourceTalentName;

  @FXML ChoiceBox destTalentName;

  @FXML TextField numTalents;

  @FXML Label numTalentsAvailable;

  @FXML Label errorMessage;

  @FXML ArrayList<Label> labelList;

  @FXML Label playerInfo;

  /**
   * Constructor that initializes the model.
   * @param model is the backend of the game.
   */
  public UpgradeTalentsActionEvenPlayersController(App model) {
    this.model = model;
    this.next = "selectActionEvenPlayers";

    territoryNames = FXCollections.observableArrayList();
    talentNamesFrom = FXCollections.observableArrayList();
    talentNamesTo = FXCollections.observableArrayList();
    talents = new HashMap<>();
    talents.put("Undergrad", "level0");
    talents.put("Masters", "level1");
    talents.put("PhD", "level2");
    talents.put("Postdoc", "level3");
    talents.put("Assistant Professor", "level4");
    talents.put("Associate Professor", "level5");
    talents.put("Professor", "level6");
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
    String playerName = model.getPlayer().getName();
    techLevel.setText(
        String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getTechLevel()));
    // setUpgradeFromChoiceBox(sourceTalentName, (String) sourceTalentName.getValue());
    // setUpgradeFromChoiceBox(destTalentName, (String) sourceTalentName.getValue());
  }

  /**
   *  Fills the choice boxes with a list of territories that a player owns.
   * After the choice boxes are filled, the player is able to select the source
   * territory for attacking.
   */
  private void setSourceTerritoryNames() {

    territoryNames.removeAll(territoryNames);
    String playerName = model.getPlayer().getName();
    HashMap<String, Territory> playerTerritories =
        model.getPlayer().getMap().getPlayerTerritories(playerName);
    for (String territoryName : playerTerritories.keySet()) {
      boolean add = false;
      HashMap<String, Integer> allNumUnits =
          model.getPlayer().getMap().getTerritory(territoryName).getAllNumUnits();
      for (String unitName : allNumUnits.keySet()) {
        if (allNumUnits.get(unitName) > 0) {
          add = true;
        }
      }
      if (add) {
        territoryNames.add(territoryName);
      }
    }
    sourceTerritoryName.getItems().addAll(territoryNames);
  }

  private void setUpgradeFromChoiceBox(ChoiceBox box, String territoryName) {
    StyleMapping mapping = new StyleMapping();
    box.getItems().removeAll(talentNamesFrom);
    talentNamesFrom.removeAll(talentNamesFrom);
    HashMap<String, Integer> numUnits =
        model.getPlayer().getMap().getTerritory(territoryName).getAllNumUnits();
    for (String unitName : numUnits.keySet()) {
      if (numUnits.get(unitName) > 0) {
        int index = Integer.valueOf(Character.toString(unitName.charAt(5)));
        talentNamesFrom.add(mapping.getTalentNames().get(index));
      }
    }
    box.getItems().addAll(talentNamesFrom);
  }

  private void setUpgradeToChoiceBox(ChoiceBox box, String territoryName) {
    StyleMapping mapping = new StyleMapping();
    box.getItems().removeAll(talentNamesTo);
    talentNamesTo.removeAll(talentNamesTo);
    ArrayList<String> talentNames = mapping.getTalentNames();
    for (String talentName : talentNames) {
      int indexTo = talentNames.indexOf(talentName);
      int indexFrom = talentNames.indexOf((String) sourceTalentName.getValue());
      if (indexTo > indexFrom) {
        talentNamesTo.add(talentName);
      }
    }
    box.getItems().addAll(talentNamesTo);
  }

  /* Populates a choice box with the names of various talents present in the game such as 'Undergrad', 'Masters', etc.
   */
  /*
  private void setUpgradeChoiceBox(ChoiceBox box) {
    talentNames.removeAll(talentNames);
    for (String name : talents.keySet()) {
      talentNames.add(name);
    }
    box.getItems().removeAll();
    box.getItems().addAll(talentNames);
  }*/

  public void onSelectSource(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof ChoiceBox) {
      // String srcTerritory = (String) sourceTerritoryName.getValue();
      String territoryName = (String) sourceTerritoryName.getValue();
      setUpgradeFromChoiceBox(sourceTalentName, territoryName);
    } else {
      throw new IllegalArgumentException("Invalid ActionEvent source " + source);
    }
  }

  public void onSelectTalent(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof ChoiceBox) {
      StyleMapping mapping = new StyleMapping();
      String srcTerritory = (String) sourceTerritoryName.getValue();
      String srcTalent = (String) sourceTalentName.getValue();
      String unitName = mapping.getTalents(srcTalent);
      HashMap<String, Integer> allNumUnits =
          model.getPlayer().getMap().getTerritory(srcTerritory).getAllNumUnits();
      numTalentsAvailable.setText(String.valueOf(allNumUnits.get(unitName)));
      setUpgradeToChoiceBox(destTalentName, srcTerritory);
    } else {
      throw new IllegalArgumentException("Invalid ActionEvent source " + source);
    }
  }

  /**
   *  Sends upgrade talent request to the server. Switches the view back to Action Selection view if successful,
   * or displays an error message.
   */
  @FXML
  public void onUpgradeTalent(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      String validate = validateAction();
      if (validate != null) {
        errorMessage.setText(validate);
      } else {
        ActionInfoFactory af = new ActionInfoFactory();
        StyleMapping mapping = new StyleMapping();
        ActionInfo info =
            af.createUpgradeUnitActionInfo(
                model.getPlayer().getName(),
                (String) sourceTerritoryName.getValue(),
                mapping.getTalents((String) sourceTalentName.getValue()),
                mapping.getTalents((String) destTalentName.getValue()),
                parseIntFromTextField(numTalents.getText()));
        String success = model.getPlayer().tryIssueUpgradeUnitOrder(info);
        if (success != null) {
          errorMessage.setText(success);
        } else {
          loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
        }
      }
    } else {
      throw new IllegalArgumentException("Invalid source " + ae.getSource() + " for this action.");
    }
  }

  /**
   *  Triggered when a player hits the cancel button.
   * Player is taken back to the select action window.
   */
  @FXML
  public void onCancel(ActionEvent ae) throws IOException {
    Object source = ae.getSource();
    if (source instanceof Button) {
      loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
    } else {
      throw new IllegalArgumentException(
          "Invalid source " + source + " for the cancel upgrade tech level method.");
    }
  }

  @FXML
  public void onTypingNumUnits(KeyEvent ke) throws Exception {}

  /**
   *  Ensures all the inputs through various buttons, textfields, choice boxes, etc are valid for creating a
   * ActionInfo object for upgrading tech talent. Returns null is choices are valid, else a string describing error.
   */
  private String validateAction() {
    try {
      validateChoiceBox(sourceTerritoryName);
      validateChoiceBox(sourceTalentName);
      validateChoiceBox(destTalentName);
      parseIntFromTextField(numTalents.getText());
    } catch (Exception e) {
      return e.getMessage();
    }
    return null;
  }

  /**
   *  Returns a string with an error description if ChoiceBox has null value, else returns a null string.
   */
  private String validateChoiceBox(ChoiceBox box) {
    String error = box + " cannot be empty.";
    try {
      if (((String) box.getValue()).equals("") || ((String) box.getValue()) == null) {
        throw new IllegalArgumentException(error);
      }
    } catch (IllegalArgumentException iae) {
      throw new IllegalArgumentException(error);
    }
    return null;
  }

  /**
   *  Returns an integer from text.
   * @param text is the string from which integer is parsed.
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

  /**
   *  Loads next phase after a player clicks on a menu item inside a SplitMenuButton.
   * @param window is the source Stage where the user interacted.
   */
  private void loadNextPhase(Stage window) throws IOException {
    Object controller = new ControllerFactory().getController(next, model);
    Stage newWindow = PhaseChanger.switchTo(window, controller, next);
    newWindow.show();
  }
}
