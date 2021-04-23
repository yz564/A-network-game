package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.ClientEvent;
import edu.duke.ece651.risk.client.ClientEventListener;
import edu.duke.ece651.risk.client.GUIEventMessenger;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;

public class WatchGameController extends Controller implements Initializable, ClientEventListener {
    @FXML Label errorMessage;
    @FXML ImageView mapImageView;
    @FXML ArrayList<ToggleButton> labelList;
    @FXML ArrayList<Label> numList;

    GUIEventMessenger messenger;
    WorldMap map;
    String playerName;
    String nextOnLeave;

    /**
     * Defines the model and the next phase to load.
     *
     * @param model is the model of the RISK game.
     */
    public WatchGameController(App model) {
        super(model);
        this.messenger = new GUIEventMessenger();
        messenger.setGUIEventListener(model);
    }

    /**
     * Sets various elements in the view to default values.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get map from client App
        this.map = model.getPlayer().getMap();
        this.playerName = model.getPlayer().getName();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set map image according to number of players
        helper.initializeMap(map, mapImageView);
        // set coloring for each territory label
        helper.initializeTerritoryLabelByOwner(map, labelList);
        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(
                model.getPlayer(), labelList, model.getPlayer().getName());
        // set total number of units for each territory
        helper.initializeTerritoryTotalNumUnitsLabels(model.getPlayer(), labelList, numList);
        // disable territory button for enemy territory
        helper.initializeTerritoryButtons(model, labelList);

        this.nextOnLeave = "joinRoom";

        model.setListener(this);
        messenger.setWaitOthers("wait others");

        System.out.println("watch game initialize finished");
    }

    public void onLeaveGame(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) { // go to join room
            model.requestLeave();
            next = nextOnLeave;
            loadNextPhase((Stage) errorMessage.getScene().getWindow());
        } else {
            throw new IllegalArgumentException(
                    "Action event " + ae.getSource() + " is invalid for onAction().");
        }
    }

    @Override
    public void onUpdateEvent(ClientEvent ce) throws Exception {
        System.out.println("LoadingController got message from client.");
        try {
            this.next = "watchGame";
            Platform.runLater(
                    () -> {
                        try {
                            loadNextPhase((Stage) errorMessage.getScene().getWindow());
                        } catch (IOException e) {
                            errorMessage.setText(e.getMessage());
                        }
                    });
        } catch (Exception e) {
            System.out.println("Exception in LoadingController's onUpdateEvent: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
