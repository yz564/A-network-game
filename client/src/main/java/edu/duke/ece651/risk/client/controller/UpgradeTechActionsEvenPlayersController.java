package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ActionInfoFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UpgradeTechActionsEvenPlayersController implements Initializable {
  App model;
  String next;
  ObservableList techLevels;

  @FXML Label currTechLevel;

  @FXML ChoiceBox newTechLevel;

  @FXML Label territoryGroupName;

  @FXML Label errorMessage;

  @FXML ArrayList<Label> labelList;

  @FXML Label playerInfo;

  public UpgradeTechActionsEvenPlayersController(App model) {
    this.model = model;
    this.next = "selectActionEvenPlayers";
    techLevels = FXCollections.observableArrayList();
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

    territoryGroupName.setText("Upgrade Technology");
    String playerName = model.getPlayer().getName();
    currTechLevel.setText(
        String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getTechLevel()));
    setUpgradeChoiceBox(newTechLevel);
  }

  /* Populates a choice box with the names of various talents present in the game such as 'Undergrad', 'Masters', etc.
   */
  private void setUpgradeChoiceBox(ChoiceBox box) {
    techLevels.removeAll(techLevels);
    for (int level = 1; level <= 7; level++) {
      techLevels.add(String.valueOf(level));
    }
    box.getItems().addAll(techLevels);
  }

  /* Asks server to upgrade the technology of the current player. If upgrade is successful, view switches to
   * Action Selection window, else an error is displayed describing the problem with the upgrade.
   */
  public void onUpgradeTech(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      String validate = validateAction();
      if (validate != null) {
        errorMessage.setText(validate);
      } else {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionInfo info =
            af.createUpgradeTechActionInfo(
                model.getPlayer().getName(), Integer.parseInt((String) newTechLevel.getValue()));
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

  /* Triggered when a player hits the cancel button.
   * Player is taken back to the select action window.
   */
  public void onCancel(ActionEvent ae) throws IOException {
    Object source = ae.getSource();
    if (source instanceof Button) {
      loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
    }
    else {
      throw new IllegalArgumentException("Invalid source " + source + " for the cancel upgrade tech level method.");
    }
  }

  public void onSelectingTechLevel() throws Exception {}

  /* Validates that the on screen options selected by the user are okay enough to create an ActionInfo object that
   * would be used to upgrade the tech.
   */
  private String validateAction() {
    try {
      validateChoiceBox(newTechLevel);
    } catch (Exception e) {
      return e.getMessage();
    }
    return null;
  }

  /* Returns a string with an error description if ChoiceBox has null value, else returns a null string.
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

  /* Loads next phase after a player clicks on a menu item inside a SplitMenuButton.
   * @param window is the source Stage where the user interacted.
   */
  private void loadNextPhase(Stage window) throws IOException {
    Object controller = new ControllerFactory().getController(next, model);
    Stage newWindow = PhaseChanger.switchTo(window, controller, next);
    newWindow.show();
  }
}
