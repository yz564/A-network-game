package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.ClientEvent;
import edu.duke.ece651.risk.client.ClientEventListener;
import edu.duke.ece651.risk.client.GUIEventMessenger;
import javafx.application.Platform;
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

/* Controller for the screen that asks user to join a room.
 */
public class JoinRoomController extends Controller
        implements Initializable, ErrorHandlingController, ClientEventListener {
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

    /**
     * Sets various elements in the view to default values.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        model.setListener(this);
        System.out.println("join Room initialize finished");
    }

    @Override
    public void onUpdateEvent(ClientEvent ce) throws Exception {
        Boolean joinRoomSuccess = ce.getStatusBoolean();
        // System.out.println("GUI message received " + joinRoomSuccess);
        if (!joinRoomSuccess) {
            Platform.runLater(() -> errorMessage.setText("The room is full! Try another room."));
        } else {
            boolean checkInSuccess = model.checkIn();
            // System.out.println("GUI checkIn " + checkInSuccess);
            if (checkInSuccess) {
                this.next = "loading";
            } else {
                this.next = "selectAction";
            }
            Platform.runLater(
                    () -> {
                        try {
                            loadNextPhase((Stage) errorMessage.getScene().getWindow());
                        } catch (IOException e) {
                            errorMessage.setText(e.getMessage());
                            System.out.println(errorMessage.getText());
                        }
                    });
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
