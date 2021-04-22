package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ActionInfoFactory;
import edu.duke.ece651.risk.shared.ActionRuleCheckerHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ResearchCloakingActionController extends ActionController {

  /**
   * Constructor that initializes the model.
   *
   * @param model is the backend of the game.
   */
  public ResearchCloakingActionController(App model, Stage mainPage) {
    super(model, null, null, mainPage);
  }

  /**
   * Sets various elements in the view to default values.
   *
   * @param location is the location of the FXML resource.
   * @param resources used to initialize the root object of the view.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    loadResourceInfo("tech");
    try {
      updateTotalCost(getActionInfo(), "tech");
    } catch (Exception e) {
      setErrorMessage(e.getMessage());
    }
  }

  /** Returns a attack ActionInfo object based on fields entered by the user in the view. */
  @Override
  protected ActionInfo getActionInfo() throws IllegalArgumentException {
    ActionInfoFactory af = new ActionInfoFactory();
    ActionRuleCheckerHelper checker = new ActionRuleCheckerHelper();
    ActionInfo info = af.createResearchCloakingActionInfo(playerName);
    String error = checker.checkRuleForResearchCloaking(info, map);
    if (error != null){
      throw new IllegalArgumentException(error);
    }
    return info;
  }

  /** Triggered when player confirms their attack action by clicking on the Confirm button. */
  @FXML
  public void onAction(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      try {
        clearErrorMessage();
        ActionInfo info = getActionInfo();
        updateTotalCost(info, "tech");
        String success = model.getPlayer().tryIssueResearchCloakingOrder(info);
        if (success != null) {
          setErrorMessage(success);
        } else {
          Stage popup = (Stage) (((Node) ae.getSource()).getScene().getWindow());
          popup.close();
          loadNextPhase(mainPage);
        }
      } catch (IllegalArgumentException e) {
        setErrorMessage(e.getMessage());
      }
    } else {
      throw new IllegalArgumentException("Invalid ActionEvent source " + source);
    }
  }
}