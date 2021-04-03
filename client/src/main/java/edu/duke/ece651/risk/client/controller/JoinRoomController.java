package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
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

/* Controller for the screen that asks user to join a room.
 */
public class JoinRoomController {
    App model;
    String next;

    @FXML
    Label joinRoomErrorLabel;

    /* Simple constructor which sets the model and the next screen.
     * @param model is the model for the RISK game.
     */
    public JoinRoomController(App model) {
        this.model = model;
        this.next = "test";
    }

    /* Loads the next Phase.
     * @param ae is used to compute the parent of the item that was interacted with in a view.
     */
    private void loadNextPhase(ActionEvent ae) throws IOException {
        Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }

    /* Lets user join a given room.
     * @param ae is used to determine if button for joining this room was clicked on.
     * @param roomId is the room number you want to join. Currently supports joining room 1, 2, 3, and 4.
     */
    private void joinRoom(ActionEvent ae, int roomId) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            Boolean success = model.tryJoinRoom(roomId);
            if (!success){
                joinRoomErrorLabel.setText("The room is full! Try another room.");
            }
            else {
                loadNextPhase(ae);
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /* Lets user join room one.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomOne(ActionEvent ae) throws Exception {
        joinRoom(ae, 1);
    }

    /* Lets user join room two.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomTwo(ActionEvent ae) throws Exception {
        joinRoom(ae, 2);
    }

    /* Lets user join room three.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomThree(ActionEvent ae) throws Exception {
        joinRoom(ae, 3);
    }

    /* Lets user join room four.
     * @param ae is used to determine if button for joining this room was clicked on.
     */
    @FXML
    public void onJoinRoomFour(ActionEvent ae) throws Exception {
        joinRoom(ae, 4);
    }
}
