package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/* Controller for the screen that asks user to join a room.
 */
public class JoinRoomController extends Controller implements Initializable, ErrorHandlingController {
    String newPlayerNextView;
    String existingPlayerNextView;

    @FXML
    Label errorMessage;

    /**
     * Simple constructor which sets the model.
     * @param model is the model for the RISK game.
     */
    public JoinRoomController(App model) {
        super(model);
    }

    /**
     * Lets user join a given room.
     * @param ae is used to determine if button for joining this room was clicked on.
     * @param roomId is the room number you want to join. Currently supports joining room 1, 2, 3, and 4.
     */
    private void joinRoom(ActionEvent ae, int roomId, String newPlayerNextView, String existingPlayerNextView) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            clearErrorMessage();
            Boolean joinRoomSuccess = model.tryJoinRoom(roomId);
            if (!joinRoomSuccess){
                errorMessage.setText("The room is full! Try another room.");
            }
            else {
                Boolean checkInSuccess = model.checkIn();
                if (checkInSuccess) {
                    //model.getPlayer().waitOtherPlayers();
                    this.next = newPlayerNextView;
                }
                else {
                    this.next = existingPlayerNextView;
                }
                loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
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
        //this.newPlayerNextView = "selectTerritoryGroup2P";
        this.newPlayerNextView = "loadSelectTerritoryGroup2P";
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

    @Override
    public void setErrorMessage(String error) {
        errorMessage.setText(error);
    }

    @Override
    public void clearErrorMessage() {
        setErrorMessage(null);
    }
}
