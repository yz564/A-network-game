package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.Player;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AllocateTalents3PController implements Initializable {
    App model;
    String next;

    @FXML
    Label territory1Label;

    @FXML
    Label territory2Label;

    @FXML
    Label territory3Label;

    @FXML
    Label territory4Label;

    @FXML
    Label territory5Label;

    public AllocateTalents3PController(App model) {
        this.model = model;
        this.next = "test";
    }

    public void initialize(URL location, ResourceBundle resources) {
        setTerritoryLabels();
    }

    private void setTerritoryLabels() {
        Player currentPlayer = model.getPlayer();
        WorldMap map = currentPlayer.getMap();
        HashMap<String, Territory> playerTerritories = map.getPlayerTerritories(currentPlayer.getName());
        Iterator<String> itr = playerTerritories.keySet().iterator();
        territory1Label.setText(itr.next());
        territory2Label.setText(itr.next());
        territory3Label.setText(itr.next());
        territory4Label.setText(itr.next());
        territory5Label.setText(itr.next());
    }
}
