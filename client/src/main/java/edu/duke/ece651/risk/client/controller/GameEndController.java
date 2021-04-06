package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class GameEndController {
  App model;
  String next;

  public GameEndController(App model) {
    this.model = model;
    this.next = "joinRoom";
  }

  @FXML Label gameEndLabel;

  @FXML
  public void onGameEnd(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      // add delete room id method
      loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
    } else {
      throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
    }
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
