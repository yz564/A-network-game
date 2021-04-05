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

public class SelectActionController implements Initializable {
    App model;
    String next;

    @FXML
    Label playerInfo;

    /* Constructor that initializes the model and the next view.
     */
    public SelectActionController(App model) {
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

    /* Switches to the view that asks player join a  room.
     */
    @FXML
    public void onLeavingGame(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) { // go to join room
            model.requestLeave();
            this.next = "joinRoom";
            loadNextPhase(ae);
        }
        else {
            throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid for onAction().");
        }
    }

    /* Takes the user to a view that asks them to wait for other players to finishe their turn.
     */
    @FXML
    public void onCompletingTurn(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if (source instanceof Button) {
            this.next = "test"; // TODO update to the next scene "waiting for other players to finish their turn view"
            loadNextPhase(ae);
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
