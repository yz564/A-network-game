package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.configuration.IMockitoConfiguration;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.assertions.api.Assertions;
import org.testfx.util.WaitForAsyncUtils;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class ServerConnectControllerTest {
    TextField testServerConnectAddress;
    Label testErrorMessage;
    Button testConnectButton;
    ServerConnectController cont;
    App model;

    @Start
    private void start(Stage stage) {
        model = new App();
        cont = new ServerConnectController(model);
        testErrorMessage = new Label();
        cont.errorMessage = testErrorMessage;
        testServerConnectAddress = new TextField();
        cont.serverConnectAddressField = testServerConnectAddress;
    }

    @Test
    public void test_invalid_server_address(FxRobot robot) {
        Platform.runLater(() -> {
            testServerConnectAddress.setText("12345");
            Button connect = new Button("Connect");
            try {
                cont.onConnectButton(new ActionEvent(connect, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(testErrorMessage, LabeledMatchers.hasText("Server address does not exist!"));
        //Assertions.assertThat(cont.errorMessage).hasText("Server address does not exist!");
        assertEquals(false, model.isConnectedToServer());
    }

    @Test
    public void test_valid_server_address(FxRobot robot) {
        Platform.runLater(() -> {
            testServerConnectAddress.setText("localhost");
            Button connect = new Button("Connect");
            try {
                cont.onConnectButton(new ActionEvent(connect, null));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        //FxAssert.verifyThat(testErrorMessage, LabeledMatchers.hasText(""));
        //Assertions.assertThat(cont.errorMessage).hasText("Server address does not exist!");
        assertEquals(true, model.isConnectedToServer());

        // get current Stage, and check its title matches the title from controller factory.
    }

    @Test
    public void test_error_handling_methods() {
        String error = "random error message";
        cont.setErrorMessage(error);
        assertEquals(true, cont.errorMessage.getText().equals(error));

        cont.clearErrorMessage();
        assertNull(cont.errorMessage.getText());
    }

}