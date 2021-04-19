package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.ClientEvent;
import edu.duke.ece651.risk.client.ClientEventListener;
import edu.duke.ece651.risk.client.GUIEventMessenger;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController extends Controller implements Initializable, ClientEventListener {
    @FXML Label loadingMessage;
    @FXML Circle circle1;
    @FXML Circle circle2;
    @FXML Circle circle3;

    String messageForPlayer;
    GUIEventMessenger messenger;

    /**
     * Defines the model and the next phase to load.
     *
     * @param model is the model of the RISK game.
     */
    public LoadingController(App model, String message) {
        super(model);
        this.messenger = new GUIEventMessenger();
        messenger.setGUIEventListener(model);
        this.messageForPlayer = message;
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

        loadingMessage.setText(messageForPlayer);
        bounceTransition(circle1, 200, -21);
        bounceTransition(circle2, 250, -15);
        bounceTransition(circle3, 200, -19);

        model.setListener(this);
        messenger.setWaitOthers("wait others");
        System.out.println("loading initialize finished");

    }

    /**
     *
     * @param myNode is the Java FX Node that you want to bounce.
     * @param duration is time in milliseconds that you want the myNode to go from lowest point to highest point.
     * @param jumpBy is the number of pixels that you want myNode to jump.
     */
    private void bounceTransition(Node myNode, int duration, int jumpBy) {
        TranslateTransition bounce = new TranslateTransition();
        bounce.setDuration(Duration.millis(duration));
        bounce.setByY(jumpBy);
        bounce.setAutoReverse(true);
        bounce.setCycleCount(Animation.INDEFINITE);
        bounce.setNode(myNode);
        bounce.play();
    }

    @Override
    @FXML
    public void onUpdateEvent(ClientEvent ce) {
        this.next = "selectTerritoryGroup";
        Platform.runLater(
                () -> {
                    try {
                        loadNextPhase((Stage) loadingMessage.getScene().getWindow());
                    } catch (IOException e) {
                        loadingMessage.setText(e.getMessage());
                    }
                });
    }
}
