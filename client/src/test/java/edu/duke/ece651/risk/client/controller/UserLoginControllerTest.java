package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.SimulateModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.LabeledMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.net.ServerSocket;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class UserLoginControllerTest {
    Stage testStage;
    Scene testScene;
    TextField testUserLoginUsernameField;
    TextField testUserLoginPasswordField;
    Label testErrorMessage;
    UserLoginController cont;
    App model;

    @Start
    private void start(Stage stage) throws Exception {
        model = new App();
        model = SimulateModel.simulate("connectServer"); // player has connected to server
        cont = new UserLoginController(model);
        testErrorMessage = new Label();
        cont.errorMessage = testErrorMessage;
        testUserLoginUsernameField = new TextField();
        cont.userLoginUsernameField = testUserLoginUsernameField;
        testUserLoginPasswordField = new TextField();
        cont.userLoginPasswordField = testUserLoginPasswordField;
    }

    @Test
    public void test_valid_login() {
        Platform.runLater(() -> {
            testUserLoginUsernameField.setText("Aman");
            testUserLoginPasswordField.setText("a");
            Button login = new Button("Log In");
            try {
                cont.logInButton(new ActionEvent(login, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(true, model.isConnectedToServer());
    }

    @Test
    public void test_invalid_login() {
        Platform.runLater(() -> {
            testUserLoginUsernameField.setText("Amandeep");
            testUserLoginPasswordField.setText("a");
            Button login = new Button("Log In");
            try {
                cont.logInButton(new ActionEvent(login, null));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        FxAssert.verifyThat(testErrorMessage, LabeledMatchers.hasText("Log in failed! Try Again."));
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