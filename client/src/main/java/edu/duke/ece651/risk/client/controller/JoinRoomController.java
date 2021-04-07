package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/* Controller for the screen that asks user to join a room.
 */
public class JoinRoomController {
    App model;
    String next;
    String newPlayerNextView;
    String existingPlayerNextView;

    @FXML
    Label joinRoomErrorLabel;

    /**
     * Simple constructor which sets the model.
     * @param model is the model for the RISK game.
     */
    public JoinRoomController(App model) {
        this.model = model;
    }

    /**
     *  Loads the next Phase.
     * @param ae is used to compute the parent of the item that interacted
     * with the view that this controller is attached to.
     */
    private void loadNextPhase(ActionEvent ae) throws IOException {
        Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }


    /**
     * Lets user join a given room.
     * @param ae is used to determine if button for joining this room was clicked on.
     * @param roomId is the room number you want to join. Currently supports joining room 1, 2, 3, and 4.
     */
    private void joinRoom(ActionEvent ae, int roomId, String newPlayerNextView, String existingPlayerNextView) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            Boolean joinRoomSuccess = model.tryJoinRoom(roomId);
            if (!joinRoomSuccess){
                joinRoomErrorLabel.setText("The room is full! Try another room.");
            }
            else {
                Boolean checkInSuccess = model.checkIn();
                if (checkInSuccess) {
                    this.next = newPlayerNextView;
                    model.getPlayer().startInitialization();
                }
                else {
                    this.next = existingPlayerNextView;
                }
                loadNextPhase(ae);
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /**
     * Lets user join room one.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomOne(ActionEvent ae) throws Exception {
        this.newPlayerNextView = "selectTerritoryGroup2P";
        this.existingPlayerNextView = "selectActionEvenPlayers";
        joinRoom(ae, 1, newPlayerNextView, existingPlayerNextView);
    }

    /**
     * Lets user join room two.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomTwo(ActionEvent ae) throws Exception {
        this.newPlayerNextView = "selectTerritoryGroup3P";
        this.existingPlayerNextView = "selectActionOddPlayers";
        joinRoom(ae, 2, newPlayerNextView, existingPlayerNextView);

    }

    /**
     * Lets user join room three.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomThree(ActionEvent ae) throws Exception {
        this.newPlayerNextView = "selectTerritoryGroup4P";
        this.existingPlayerNextView = "selectActionEvenPlayers";
        joinRoom(ae, 3, newPlayerNextView, existingPlayerNextView);
    }

    /**
     * Lets user join room four.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomFour(ActionEvent ae) throws Exception {
        this.newPlayerNextView = "selectTerritoryGroup5P";
        this.existingPlayerNextView = "selectActionOddPlayers";
        joinRoom(ae, 4, newPlayerNextView, existingPlayerNextView);
    }
}
