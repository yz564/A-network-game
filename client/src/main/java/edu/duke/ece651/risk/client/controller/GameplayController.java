package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameplayController implements Initializable {
    App model;
    String next;

    @FXML
    Label playerName;

    @FXML
    Label techLevel;

    @FXML
    Label food;

    @FXML
    Label money;

    /* Constructor that initializes the model and the next view.
     */
    public void GameplayController(App model) {
        this.model = model;
        this.next = "test";
    }

    /* Sets various labels in the view to help player understand their status in the game.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //playerName.setText(model.getPlayer().getName());
        // set player color
        //techLevel.setText(model.getPlayer().getTechLevel());
        //food.setText(model.getPlayer().getAmountOfFood());
        //money.setText(model.getPlayer().getAmountOfMoney());
    }

    /* Switches to a view that asks player to choose an action such as attack another territory.
     */
    public void onAction(ActionEvent ae) throws IllegalArgumentException {
        Object source = ae.getSource();
        if (source instanceof Button) {
            // loadNextPhase();
        }
        else {
            throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid for onAction().");
        }
    }

    /* Loads the next Phase.
     * @param ae is used to compute the parent of the item that interacted
     * with the view that this controller is attached to.
     */
    private void loadNextPhase(ActionEvent ae) throws IOException {
        Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }
}
