package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserLoginController {
    App model;
    String next;

    public UserLoginController(App model){
        this.model = model;
        this.next = "joinRoom";
    }
    @FXML
    TextField userLoginUsernameField;
    @FXML
    TextField userLoginPasswordField;
    @FXML
    Label userLoginErrorLabel;

    @FXML
    public void logInButton(ActionEvent ae) throws Exception {

        Object source = ae.getSource();
        if (source instanceof Button) {
            String username = userLoginUsernameField.getText();
            String password = userLoginPasswordField.getText();
            boolean success = model.tryLogin(username, password);
            if (!success){
                userLoginErrorLabel.setText("Log in failed! Try Again.");
            }
            else{
                Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
                Object controller = new ControllerFactory().getController(next, model);
                Stage newWindow = PhaseChanger.switchTo(window, controller, next);
                newWindow.show();
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
