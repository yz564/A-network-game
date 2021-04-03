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

public class ServerConnectController {
    App model;

    public ServerConnectController(App model) {
        this.model = model;
    }

    @FXML
    TextField serverConnectAddressField;
    @FXML
    Label serverConnectErrorMessage;

    @FXML
    public void onConnectButton(ActionEvent ae) throws Exception {

        Object source = ae.getSource();
        if (source instanceof Button) {
            String serverAdd = serverConnectAddressField.getText();
            String serverMsg = model.tryConnect(serverAdd);
            if (serverMsg != null){
                serverConnectErrorMessage.setText(serverMsg);
            }
            else{
            URL xmlResource = getClass().getResource("/ui/views/user-login.fxml");
            FXMLLoader loader = new FXMLLoader(xmlResource);
            loader.setControllerFactory(c -> {
                return new UserLoginController(model);
            });
            Pane p = loader.load();
            Scene nextScene = new Scene(p);

            Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
            window.setScene(nextScene);
            window.setTitle("Duke Risk Game! - Log In");
            window.show();
        }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
