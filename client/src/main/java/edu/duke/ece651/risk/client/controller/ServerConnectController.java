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

public class ServerConnectController {
    App model;
    String next;

    public ServerConnectController(Object model) {
        this.model = (App) model;
        this.next = "userLogin";
    }

    @FXML
    TextField serverConnectAddressField;
    @FXML
    Label serverConnectErrorLabel;

    @FXML
    public void onConnectButton(ActionEvent ae) throws Exception {

        Object source = ae.getSource();
        if (source instanceof Button) {
            String serverAdd = serverConnectAddressField.getText();
            String serverMsg = model.tryConnect(serverAdd);
            if (serverMsg != null){
                serverConnectErrorLabel.setText(serverMsg);
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
