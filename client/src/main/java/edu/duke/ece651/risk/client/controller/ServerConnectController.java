package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.ServerConnector;
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
    ServerConnector model;

    public ServerConnectController(){
        this.model = new ServerConnector();
    }
    @FXML
    TextField serverConnectAddressField;
    @FXML
    Label serverConnectErrorMessage;

    @FXML
    public void onConnectButton(ActionEvent ae) throws Exception {

        Object source = ae.getSource();
        if (source instanceof Button) {
            //Button btn = (Button) source;
            String serverAdd = serverConnectAddressField.getText();
            String serverMsg = model.tryConnect(serverAdd);
            if (serverMsg != null){
                serverConnectErrorMessage.setText(serverMsg);
            }
            else{
                URL xmlResource = getClass().getResource("/ui/views/user-login.fxml");
                Pane p = FXMLLoader.load(xmlResource);
                Scene nextScene = new Scene(p);

                Stage window = (Stage)  (((Node)ae.getSource()).getScene().getWindow());
                window.setScene(nextScene);
                window.setTitle("Duke Risk Game! - Log In");
                window.show();
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
