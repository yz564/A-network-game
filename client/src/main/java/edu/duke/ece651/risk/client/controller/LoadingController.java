package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.ClientEvent;
import edu.duke.ece651.risk.client.ClientEventListener;
import edu.duke.ece651.risk.client.GUIEventMessenger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController extends Controller implements Initializable, ClientEventListener {

    @FXML Label loadingMessage;

    GUIEventMessenger messenger;

    /**
     * Defines the model and the next phase to load.
     *
     * @param model is the model of the RISK game.
     */
    public LoadingController(App model) {
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
      //model.getPlayer().waitOtherPlayers();
      //model.getMessenger().setClientEventListener(this);
      //messenger.setWaitOthers("wait others");
      model.setListener(this);
      messenger.setWaitOthers("wait others");
      System.out.println("loading initialize finished");
    }

    @Override
    @FXML
    public void onUpdateEvent(ClientEvent ce) throws Exception {
        // gets new map from initialization.
        this.next = "selectTerritoryGroup";
        loadNextPhase((Stage) loadingMessage.getScene().getWindow());
    }

    public void onMouseMoved(MouseEvent mouseEvent) {
      //messenger.setWaitOthers("wait others");
    }
}
