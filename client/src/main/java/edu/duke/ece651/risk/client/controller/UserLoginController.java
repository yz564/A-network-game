package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
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

import java.net.URL;

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
            //Button btn = (Button) source;
            String username = userLoginUsernameField.getText();
            String password = userLoginPasswordField.getText();
            String serverMsg = model.tryLogin(username, password);
            if (serverMsg != null){
                userLoginErrorMessage.setText(serverMsg);
            }
            else{
                URL xmlResource = getClass().getResource("/ui/views/test.fxml");
                FXMLLoader loader = new FXMLLoader(xmlResource);
                loader.setControllerFactory(c -> {
                    return new JoinRoomController(model);
                });
                Pane p = loader.load();
                Scene nextScene = new Scene(p);

                Stage window = (Stage)  (((Node)ae.getSource()).getScene().getWindow());
                window.setScene(nextScene);
                window.setTitle("Duke Risk Game! - Select Room");
                window.show();
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
