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
    String nextOnCompleteTurn;
    String nextOnLeave;
    String nextOnMoveAction;
    String nextOnAttackAction;
    String nextOnUpgradeTalentsAction;
    String nextOnUpgradeTechAction;

    @FXML
    Label playerInfo;

    /* Constructor that initializes the model and the next view.
     */
    public SelectActionController(App model) {
        this.model = model;
    }

    /* Sets various labels in the view to help player understand their status in the game.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (model.getPlayer().getMap().getNumPlayers() % 2 == 0) {
            this.nextOnCompleteTurn = "selectActionEvenPlayers";
            this.nextOnMoveAction = "moveActionEvenPlayers";
            this.nextOnAttackAction = "attackActionEvenPlayers";
            this.nextOnUpgradeTalentsAction = "upgradeTalentsActionEvenPlayers";
            this.nextOnUpgradeTechAction = "upgradeTechActionEvenPlayers";
        }
        else {
            this.nextOnCompleteTurn = "selectActionOddPlayers";
            this.nextOnMoveAction = "moveActionOddPlayers";
            this.nextOnAttackAction = "attackActionOddPlayers";
            this.nextOnUpgradeTalentsAction = "upgradeTalentsActionOddPlayers";
            this.nextOnUpgradeTechAction = "upgradeTechActionOddPlayers";
        }
        this.nextOnLeave = "joinRoom";
    }

    /* Opens a new view that asks user to move their talents within their territories.
     */
    public void onSelectMove() {

    }

    /* Opens a new view that asks user to attack enemy territory.
     */
    public void onSelectAttack() {

    }

    /* Opens a new view that asks user to upgrade their talents.
     */
    public void onSelectUpgradeTalents() {

    }

    /* Opens a new view that asks user to upgrade their technology level.
     */
    public void onSelectUpgradeTech() {

    }

    /* Switches to the view that asks player join a  room.
     */
    @FXML
    public void onLeavingGame(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) { // go to join room
            model.requestLeave();
            next = nextOnLeave;
            loadNextPhase(ae);
        }
        else {
            throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid for onAction().");
        }
    }

    /* Takes the user to a view that asks them to wait for other players to finish their turn.
     */
    @FXML
    public void onCompletingTurn(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            String status = model.getPlayer().doneIssueOrders();
            if (status != null) {
                // show message in new screen
            }
            else {
                this.next = nextOnCompleteTurn;
                loadNextPhase(ae);
            }
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
