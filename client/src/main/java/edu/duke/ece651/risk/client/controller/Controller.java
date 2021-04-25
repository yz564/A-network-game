package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Provides functionality to initialize contents of the view and load the next phase.
 */
public abstract class Controller implements Initializable, ErrorHandlingController {
    App model;
    String next;
    @FXML Label errorMessage;
    private double xOffset;
    private double yOffset;

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
    public void initialize(URL location, ResourceBundle resources) {}

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

    /**
     * Loads the next Phase.
     * with the view that this controller is attached to.
     */
    protected void loadActionPopup(Stage currentWindow, String srcName, String destName) throws IOException {
        Object controller = new ControllerFactory().getActionController(next, model, srcName, destName, currentWindow);
        Stage popup = PhaseChanger.switchTo(new Stage(), controller, next);
        popup.initStyle(StageStyle.UNDECORATED);
        popup.setOpacity(0.9);
        popup.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        popup.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popup.setX(event.getScreenX() - xOffset);
                popup.setY(event.getScreenY() - yOffset);
            }
        });
        popup.show();
    }

    @Override
    public void setErrorMessage(String error) {
        errorMessage.setText(error);
    }

    @Override
    public void clearErrorMessage() {
        setErrorMessage(null);
    }
}
