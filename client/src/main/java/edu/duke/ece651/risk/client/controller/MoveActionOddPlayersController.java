package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MoveActionOddPlayersController implements Initializable {
    App model;
    String next;

    public MoveActionOddPlayersController(App model) {
        this.model = model;
        this.next = "selectActionOddPlayers";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onTypingNumUnits(KeyEvent ke) throws Exception {
    }

    public void onMove(ActionEvent ae) throws Exception {
    }
}
