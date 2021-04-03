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

public class ServerConnectController {
    App model;

    public ServerConnectController(Object model) {
        this.model = (App) model;
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
            Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
            Stage newWindow = WindowChanger.switchTo(window, model, "userLogin");
            newWindow.show();
        }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }
}
