package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class GameEndController extends Controller implements Initializable {
  Boolean won;
  String verdict;
  String reason;
  String imagePath;

  public GameEndController(App model) {
    super(model);
    this.next = "joinRoom";
  }

  @FXML Label gameEndLabel;
  @FXML Label gameEndSubLabel;
  @FXML ImageView gameEndImage;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    if (model.getPlayer().isWin()) {
      verdict = "You won!";
      imagePath = "/ui/static-images/end-game/win-game.gif";
    }
    else {
      verdict = "You lost!";
      imagePath = "/ui/static-images/end-game/lose-game.gif";
    }
    reason = model.getPlayer().getGameOverMessage();
    gameEndLabel.setText(verdict);
    gameEndSubLabel.setText(reason);
    gameEndImage.setImage(new Image(getClass().getResource(imagePath).toString()));
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

  /**
   * Quits the game by Exiting the Java FX application.
   * @param ae is user clicking on the Quit Game button in GUI that triggers this function.
   */
  @FXML
  public void onGameQuit(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      Platform.exit();
    }
    else {
      throw new IllegalArgumentException("Invalid source " + source + " for quit game button.");
    }
  }
}
