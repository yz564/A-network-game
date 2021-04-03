package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.WindowChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserLoginController {
    App model;

    public UserLoginController(App model){
        this.model = model;
    }
    @FXML
    TextField userLoginUsernameField;
    @FXML
    TextField userLoginPasswordField;
    @FXML
    Label userLoginErrorMessage;

    @FXML
    public void logInButton(ActionEvent ae) throws Exception {

        Object source = ae.getSource();
        if (source instanceof Button) {
            String username = userLoginUsernameField.getText();
            String password = userLoginPasswordField.getText();
            boolean success = model.tryLogin(username, password);
            if (!success){
                userLoginErrorMessage.setText("Log in failed! Try Again.");
            }
            else{
                Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
                window = WindowChanger.switchTo(window, model, "userLogin");
                window.show();
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
