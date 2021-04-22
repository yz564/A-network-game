package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ServerConnectController extends Controller implements ErrorHandlingController {
    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public ServerConnectController(Object model) {
        super((App) model);
        this.next = "userLogin";
    }

    @FXML Pane root;
    @FXML TextField serverConnectAddressField;
    @FXML Label errorMessage;

    /**
     * Sets focus on the root pane rather than the text field.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            root.requestFocus();
        });
    }

    /**
     * @param ae
     * @throws Exception
     */
    @FXML
    public void onConnectButton(ActionEvent ae) throws Exception {

        Object source = ae.getSource();
        if (source instanceof Button) {
            clearErrorMessage();
            String serverAdd = serverConnectAddressField.getText();
            String serverMsg = model.tryConnect(serverAdd);
            // m = new GUIEventMessenger()
            // m.setServerAddress(); // controller send address to client

            // serverMsg should be get from ClientEvent, if the message is null, the @Override
            // onServerMsgUpdate() should load next phase
            if (serverMsg != null) {
                setErrorMessage(serverMsg);
            } else {
                Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
                Object controller = new ControllerFactory().getController(next, model);
                Stage newWindow = PhaseChanger.switchTo(window, controller, next);
                newWindow.show();
            }
            // everything above should be in listener's @Override onServerMsgUpdate()
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    @Override
    public void setErrorMessage(String error) {
        errorMessage.setText(error);
    }

    @Override
    public void clearErrorMessage() {
        setErrorMessage(null);
    }
}
