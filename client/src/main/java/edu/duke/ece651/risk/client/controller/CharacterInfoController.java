package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CharacterInfoController extends Controller implements Initializable {

    public CharacterInfoController(App model) {
        super(model);
    }

    @FXML
    public void onProceed(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if (source instanceof Button) {
            this.next = "selectTerritoryGroup";
            loadNextPhase((Stage) (((Node) source).getScene().getWindow()));
        }
        else {
            throw new IllegalArgumentException("Invalid action event for " + source);
        }
    }
}
