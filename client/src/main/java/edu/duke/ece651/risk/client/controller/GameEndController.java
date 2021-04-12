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

public class GameEndController extends Controller implements Initializable {
  public GameEndController(App model) {
    super(model);
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
      loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
    } else {
      throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
    }
  }
}
