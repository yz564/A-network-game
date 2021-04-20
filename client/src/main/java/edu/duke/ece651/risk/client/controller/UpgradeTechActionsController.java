package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.ActionCostCalculator;
import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ActionInfoFactory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpgradeTechActionsController extends Controller implements Initializable, ErrorHandlingController {
  ObservableList techLevels;
  WorldMap map;

  @FXML ImageView mapImageView;

  @FXML Label currTechLevel;

  @FXML ChoiceBox newTechLevel;

  @FXML Label territoryGroupName;

  @FXML Label errorMessage;

  @FXML ArrayList<ToggleButton> labelList;

  @FXML Label playerInfo;

  @FXML Label techCost;

  @FXML Label techAvailable;

  /**
   * Constructor that initializes the model.
   * @param model is the backend of the game.
   */
  public UpgradeTechActionsController(App model) {
    super(model);
    this.next = "selectAction";
    techLevels = FXCollections.observableArrayList();
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

    territoryGroupName.setText("Upgrade Technology");
    String playerName = model.getPlayer().getName();
    currTechLevel.setText(
        String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getTechLevel()));
    setUpgradeChoiceBox(newTechLevel);

    // set tech resources
    techCost.setText("0");
    techAvailable.setText(String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getResTotals().get("tech")));
  }

  /**
   * Populates a choice box with the names of various talents present in the game such as 'Undergrad', 'Masters', etc.
   */
  private void setUpgradeChoiceBox(ChoiceBox box) {
    techLevels.removeAll(techLevels);
    for (int level = 1; level <= 6; level++) {
      techLevels.add(String.valueOf(level));
    }
    box.getItems().addAll(techLevels);
  }

  /**
   * Asks server to upgrade the technology of the current player. If upgrade is successful, view switches to
   * Action Selection window, else an error is displayed describing the problem with the upgrade.
   */
  @FXML
  public void onUpgradeTech(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      clearErrorMessage();
      String validate = checkInput();
      if (validate != null) {
        errorMessage.setText(validate);
      } else {
        ActionInfo info = getUpgradeTechActionInfo();
        String success = model.getPlayer().tryIssueUpgradeTechOrder(info);
        if (success != null) {
          errorMessage.setText(success);
        } else {
          loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
        }
      }
    } else {
      throw new IllegalArgumentException("Invalid source " + ae.getSource() + " for this method.");
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
   * Displays the cost of upgrading the tech level.
   */
  @FXML
  public void onSelectingTechLevel(MouseEvent me) throws Exception {
    clearErrorMessage();
    String validChoice = validateChoiceBox(newTechLevel);
    if (validChoice != null) {
      techCost.setText("0");
    }
    else {
      ActionInfo upgradeTechInfo = getUpgradeTechActionInfo();
      ActionCostCalculator calc = new ActionCostCalculator();
      int cost = calc.calculateCost(upgradeTechInfo, model.getPlayer().getMap()).get("tech");
      techCost.setText(String.valueOf(cost));
    }
  }

  /**
   * Returns a upgrade tech ActionInfo object based on new tech level selected by the player in the view.
   */
  private ActionInfo getUpgradeTechActionInfo() {
    ActionInfoFactory af = new ActionInfoFactory();
    ActionInfo info =
            af.createUpgradeTechActionInfo(
                    model.getPlayer().getName(), Integer.parseInt((String) newTechLevel.getValue()));
    return info;
  }

  /**
   * Validates that the on screen options selected by the user are okay enough to create an ActionInfo object that
   * would be used to upgrade the tech.
   */
  private String checkInput() {
    try {
      validateChoiceBox(newTechLevel);
    } catch (Exception e) {
      return e.getMessage();
    }
    return null;
  }

  /**
   * Returns a string with an error description if ChoiceBox has null value, else returns a null string.
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

  @Override
  public void setErrorMessage(String error) {
    errorMessage.setText(error);
  }

  @Override
  public void clearErrorMessage() {
    setErrorMessage(null);
  }
}
