package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Provides functionality to initialize contents of the view and load the next phase.
 */
public abstract class Controller implements Initializable {
    App model;
    String next;

    /**
     * Defines the model and the next phase to load.
     * @param model is the model of the RISK game.
     */
    public Controller (App model) {
        this.model = model;
        this.next = "test";
    }

    /**
     * Initializes the view.
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    /**
     * Loads the next Phase.
     * @param window is the current view.
     * with the view that this controller is attached to.
     */
    protected void loadNextPhase(Stage window) throws IOException {
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }
}
