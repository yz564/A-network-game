package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoadSelectTerritoryGroup2P extends Controller implements Initializable {

    @FXML Label loadingMessage;

    /**
     * Defines the model and the next phase to load.
     *
     * @param model is the model of the RISK game.
     */
    public LoadSelectTerritoryGroup2P(App model) {
        super(model);
    }

    /**
     * Sets various elements in the view to default values.
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
      
    }

    @FXML
    public void onLoadSelectTerritoryGroups(MouseEvent me) {
        try {
            this.next = "selectTerritoryGroup2P";
            model.getPlayer().startInitialization();
            //loadNextPhase((Stage) loadingMessage.getScene().getWindow()); //if inside initialize
            loadNextPhase((Stage) (((Node) me.getSource()).getScene().getWindow())); // if inside move mouse
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
