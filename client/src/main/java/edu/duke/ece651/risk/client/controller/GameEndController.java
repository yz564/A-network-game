package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameEndController implements Initializable {
  App model;
  String next;

  public GameEndController(App model) {
    this.model = model;
    this.next = "joinRoom";
  }

  @FXML Label gameEndLabel;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    gameEndLabel.setText(model.getPlayer().getGameOverMessage());
  }

  @FXML
  public void onGameEnd(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      // add delete room id method
      int roomId = model.getPlayer().getRoomId();
      model.deleteJoinedRoomId(roomId);
      loadNextPhase(ae);
    } else {
      throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
    }
  }

  /* Loads next phase after a player clicks on a menu item inside a SplitMenuButton.
   * @param window is the source Stage where the user interacted.
   */
  private void loadNextPhase(ActionEvent ae) throws IOException {
    Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
    Object controller = new ControllerFactory().getController(next, model);
    Stage newWindow = PhaseChanger.switchTo(window, controller, next);
    newWindow.show();
  }
}
