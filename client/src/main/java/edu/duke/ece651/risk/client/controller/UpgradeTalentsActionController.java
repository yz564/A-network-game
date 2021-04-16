package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.client.view.StyleMapping;
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

public class UpgradeTalentsActionController extends Controller implements Initializable, ErrorHandlingController {
  ObservableList territoryNames;
  ObservableList talentNamesFrom;
  ObservableList talentNamesTo;
  HashMap<String, String> talents;
  HashSet<KeyCode> keysDynamicNumTalent;
  WorldMap map;

  @FXML ImageView mapImageView;

  @FXML ChoiceBox sourceTerritoryName;

  @FXML ChoiceBox sourceTalentName;

  @FXML ChoiceBox destTalentName;

  @FXML Label techLevel;

  @FXML Label numTalentsAvailable;

  @FXML TextField numTalents;

  @FXML ArrayList<Label> labelList;

  @FXML Label playerInfo;

  @FXML Label techCost;

  @FXML Label techAvailable;

  @FXML Label errorMessage;

  /**
   * Constructor that initializes the model.
   * @param model is the backend of the game.
   */
  public UpgradeTalentsActionController(App model) {
    super(model);
    this.next = "selectAction";

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

    keysDynamicNumTalent = new HashSet<>();
    keysDynamicNumTalent.add(KeyCode.DIGIT0);
    keysDynamicNumTalent.add(KeyCode.DIGIT1);
    keysDynamicNumTalent.add(KeyCode.DIGIT2);
    keysDynamicNumTalent.add(KeyCode.DIGIT3);
    keysDynamicNumTalent.add(KeyCode.DIGIT4);
    keysDynamicNumTalent.add(KeyCode.DIGIT5);
    keysDynamicNumTalent.add(KeyCode.DIGIT6);
    keysDynamicNumTalent.add(KeyCode.DIGIT7);
    keysDynamicNumTalent.add(KeyCode.DIGIT0);
    keysDynamicNumTalent.add(KeyCode.DIGIT9);
    keysDynamicNumTalent.add(KeyCode.TAB);
    keysDynamicNumTalent.add(KeyCode.BACK_SPACE);

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
    helper.initializeMap(map, mapImageView);// set coloring for each territory label
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

    // set available food amount
    techAvailable.setText(String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getResTotals().get("tech")));
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

  /**
   * Sets the choice box with units that a player could upgrade FROM.
   * @param box is the choice box that will be populated.
   * @param territoryName is the territory name in which unit to be upgraded are present.
   */
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

  /**
   * Sets the choice box with units that a player could upgrade TO.
   * @param box is the choice box that will be populated.
   * @param territoryName is the territory name in which unit to be upgraded are present.
   */
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

  public void onSelectSource(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof ChoiceBox) {
      clearErrorMessage();
      String territoryName = (String) sourceTerritoryName.getValue();
      setUpgradeFromChoiceBox(sourceTalentName, territoryName);
    } else {
      throw new IllegalArgumentException("Invalid ActionEvent source " + source);
    }
  }

  public void onSelectTalent(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof ChoiceBox) {
      clearErrorMessage();
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
      clearErrorMessage();
      String validate = checkInput();
      if (validate != null) {
        setErrorMessage(validate);
      } else {
        ActionInfo info = getUpgradeTalentInfo();
        String success = model.getPlayer().tryIssueUpgradeUnitOrder(info);
        if (success != null) {
          setErrorMessage(success);
        } else {
          loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
        }
      }
    } else {
      throw new IllegalArgumentException("Invalid source " + ae.getSource() + " for this action.");
    }
  }

  /**
   * Returns an upgrade talent ActionInfo object based on fields entered by the user in the view.
   */
  private ActionInfo getUpgradeTalentInfo() {
    ActionInfoFactory af = new ActionInfoFactory();
    StyleMapping mapping = new StyleMapping();
    ActionInfo info =
        af.createUpgradeUnitActionInfo(
            model.getPlayer().getName(),
            (String) sourceTerritoryName.getValue(),
            mapping.getTalents((String) sourceTalentName.getValue()),
            mapping.getTalents((String) destTalentName.getValue()),
            parseIntFromTextField(numTalents.getText()));
    return info;
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

  /**
   * Displays the cost of upgrade talent action dynamically when user enters
   * the source territory, source unit type, target unit type and the number
   * of units that they wish to upgrade.
   */
  @FXML
  public void onTypingNumUnits(KeyEvent ke) throws Exception {
    Object source = ke.getCode();
    if (this.keysDynamicNumTalent.contains(source)) {
      clearErrorMessage();
      ActionInfo upgradeTalentInfo = getUpgradeTalentInfo();
      ActionCostCalculator calc = new ActionCostCalculator();
      int cost = calc.calculateCost(upgradeTalentInfo, model.getPlayer().getMap()).get("tech");
      String isValid = checkInput();
      if (isValid != null) {
        setErrorMessage(isValid);
        techCost.setText("0");
      }
      else {
        techCost.setText(String.valueOf(cost));
      }
    }
  }

  /**
   * Ensures all the inputs required to make a valid move are valid and returns a null string.
   * Returns a string with a descriptive error if check fails.
   */
  private String checkInput() {
    try {
      validateChoiceBox(sourceTerritoryName);
      validateChoiceBox(sourceTalentName);
      validateChoiceBox(destTalentName);
      if (parseIntFromTextField(numTalents.getText()) < 0) {
        throw new IllegalArgumentException("Please enter a non-negative number for number of talents.");
      }
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

  @Override
  public void setErrorMessage(String error) {
    errorMessage.setText(error);
  }

  @Override
  public void clearErrorMessage() {
    setErrorMessage(null);
  }
}
