package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class AttackActionEvenPlayersController implements Initializable {
    App model;
    String next;

    public AttackActionEvenPlayersController(App model) {
        this.model = model;
        this.next = "selectActionEvenPlayers";
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
