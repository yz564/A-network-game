package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.SimulateModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class JoinRoomControllerTest {
    Stage stage;
    Button testRoomOneButton;
    Button testRoomTwoButton;
    Button testRoomThreeButton;
    Button testRoomFourButton;
    Label testErrorMessage;

    JoinRoomController cont;
    App model;

    @Start
    private void start(Stage stage) {
        try {
            this.stage = stage;
            model = new App();
            model = SimulateModel.simulate("userLogin"); // player has logged in
            cont = new JoinRoomController(model);
            testErrorMessage = new Label();
            cont.errorMessage = testErrorMessage;
            testRoomOneButton = new Button("joinRoomOneButton");
            cont.joinRoomOneButton = testRoomOneButton;
            cont.joinRoomTwoButton = testRoomTwoButton;
            cont.joinRoomThreeButton = testRoomThreeButton;
            cont.joinRoomFourButton = testRoomFourButton;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    @Test
    public void test_join_room() {
        Platform.runLater(() -> {
            Button roomOne = new Button("joinRoomOneButton");
            Button roomTwo = new Button("joinRoomTwoButton");
            Button roomThree = new Button("joinRoomThreeButton");
            Button roomFour = new Button("joinRoomFourButton");
            try {
                cont.onJoinRoomOne(new ActionEvent(roomOne, null));
                cont.onJoinRoomTwo(new ActionEvent(roomTwo, null));
                cont.onJoinRoomThree(new ActionEvent(roomThree, null));
                cont.onJoinRoomFour(new ActionEvent(roomFour, null));
            }
            catch (Exception e) {

            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(true, model.isConnectedToServer());
        //assertEquals(true, model.hasJoinedRoom(1));
        // check if new window opens up --> stage.getWindow and WindowMatcher("title") should be true

    }
}