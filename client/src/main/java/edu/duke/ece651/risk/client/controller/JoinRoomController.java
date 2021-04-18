package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.ClientEvent;
import edu.duke.ece651.risk.client.ClientEventListener;
import edu.duke.ece651.risk.client.GUIEventMessenger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

/* Controller for the screen that asks user to join a room.
 */
public class JoinRoomController extends Controller
        implements Initializable, ErrorHandlingController, ClientEventListener {
    String newPlayerNextView = "selectTerritoryGroup";
    String existingPlayerNextView = "selectAction";

    @FXML Label errorMessage;
    GUIEventMessenger messenger;

    /**
     * Simple constructor which sets the model.
     *
     * @param model is the model for the RISK game.
     */
    public JoinRoomController(App model) {
        super(model);
        this.messenger = new GUIEventMessenger();
        messenger.setGUIEventListener(model);
    }

    /*
    private void joinRoom(Boolean joinRoomSuccess) throws Exception {
        if (!joinRoomSuccess) {
            errorMessage.setText("The room is full! Try another room.");
        } else {
            // TODO
            Boolean checkInSuccess = model.checkIn();
            if (checkInSuccess) {
                model.getPlayer().waitOtherPlayers();
                model.getPlayer().startInitialization();
                this.next = this.newPlayerNextView;
            } else {
                this.next = this.existingPlayerNextView;
            }
            loadNextPhase((Stage) errorMessage.getScene().getWindow());
        }
    }
     */

    @Override
    public void onUpdateEvent(ClientEvent ce) throws Exception {
        // gets the joinRoomStatus Boolean from client
        Boolean joinRoomSuccess = ce.getStatusBoolean();
        if (!joinRoomSuccess) {
            errorMessage.setText("The room is full! Try another room.");
        } else {
            boolean checkInSuccess = model.checkIn();
            if (checkInSuccess) {
                // TODO, change this to set GUIEvent
                model.getPlayer().waitOtherPlayers();
                this.next = "loading";
            } else {
                this.next = "selectAction";
            }
            loadNextPhase((Stage) errorMessage.getScene().getWindow());
        }
    }

    /**
     * @param ae is used to determine if button for joining this room was clicked on.
     * @param roomId is the room number you want to join. Currently supports joining room 1, 2, 3,
     *     and 4.
     * @throws Exception if the event source is not a button.
     */
    private void onRoomID(ActionEvent ae, int roomId) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            clearErrorMessage();
            // sends roomId to client
            messenger.setRoomId(roomId);
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /**
     * Lets user join room one.
     *
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomOne(ActionEvent ae) throws Exception {
        onRoomID(ae, 1);
    }

    /**
     * Lets user join room two.
     *
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomTwo(ActionEvent ae) throws Exception {
        onRoomID(ae, 2);
    }

    /**
     * Lets user join room three.
     *
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomThree(ActionEvent ae) throws Exception {
        onRoomID(ae, 3);
    }

    /**
     * Lets user join room four.
     *
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomFour(ActionEvent ae) throws Exception {
        onRoomID(ae, 4);
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
