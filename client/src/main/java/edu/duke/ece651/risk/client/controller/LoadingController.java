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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.ResourceBundle;

public class LoadingController extends Controller implements Initializable, ClientEventListener {
    @FXML Label loadingMessage;
    @FXML Circle circle1;
    @FXML Circle circle2;
    @FXML Circle circle3;
    @FXML Label triviaText;
    @FXML ImageView triviaIcon;

    String messageForPlayer;
    String[] actionNames;
    GUIEventMessenger messenger;

    /**
     * Defines the model and the next phase to load.
     *
     * @param model is the model of the RISK game.
     */
    public LoadingController(App model, String message) {
        super(model);
        actionNames = new String[] {"move", "attack", "spy", "cloaking", "upgrade", "patent"};
        this.messenger = new GUIEventMessenger();
        messenger.setGUIEventListener(model);
        this.messageForPlayer = message;
    }

    /**
     * Get action description. This will be used in showing trivia on the loading page.
     * @return a hashmap of action name and their description.
     */
    private HashMap<String, String> getActionDescription() {
        HashMap<String, String> descriptions = new HashMap<>();
        descriptions.put("move", "Moving units enables you to execute better attacks later. "+
                "Units could only be moved within ones own territories.");
        descriptions.put("attack", "Attacking is a crucial part of the game. "+
                "You could only attack a territory that is adjacent to your territory.");
        descriptions.put("spy", "Once a spy is in enemy territory, "+
                "you can see detailed information about the enemy territory. "+
                "Spy can be moved one territory at a time. "+
                "You could convert undergrad units to spy units.");
        descriptions.put("cloaking", "Cloaking your territory hides information from the enemy about your territory "+
                "even if your territory is adjacent to enemy's territory. "+
                "An enemy will, however, still see detailed territory information if they have a spy in "+
                "your territory. To use cloaking, you must first research it.");
        descriptions.put("upgrade", "Upgrading is a crucial part of the game. "+
                "You could upgrade individual units, and technology level of your territories.");
        descriptions.put("patent", "Finishing researching a patent for your character is another way to win the "+
                "game. You must start the research for the patent. Once the research is started, the research "+
                "progresses automatically in each turn. The research is faster is you own territories related "+
                "to the area of the research of your character.");
        return descriptions;
    }

    /**
     * Get icon path for all types of actions. This will be used when showing a particular icon for a given action.
     * @return a hashmap of action name and their respective icon path.
     */
    private HashMap<String, String> getIconPaths() {
        HashMap<String, String> paths = new HashMap<>();
        paths.put("move", "/ui/icons/move.png");
        paths.put("attack", "/ui/icons/attack.png");
        paths.put("spy", "/ui/icons/moveSpy.png");
        paths.put("cloaking", "/ui/icons/cloaking.png");
        paths.put("upgrade", "/ui/icons/upgradeTalents.png");
        paths.put("patent", "/ui/icons/researchPatent.png");
        return paths;
    }

    /**
     * Sets various elements in the view to default values.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadingMessage.setText(messageForPlayer);
        bounceTransition(circle1, 200, -21);
        bounceTransition(circle2, 250, -15);
        bounceTransition(circle3, 200, -19);

        Random random = new Random();
        Integer actionItem = random.nextInt(actionNames.length);
        triviaText.setText(getActionDescription().get(actionNames[actionItem]));
        triviaIcon.setImage(new Image(getClass().getResource(getIconPaths().get(actionNames[actionItem])).toString()));
        triviaIcon.getStyleClass().addAll("action-button", "action-" + "move");

        model.setListener(this);
        messenger.setWaitOthers("wait others");
        System.out.println("loading initialize finished");
    }

    /**
     * @param myNode is the Java FX Node that you want to bounce.
     * @param duration is time in milliseconds that you want the myNode to go from lowest point to
     *     highest point.
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
    public void onUpdateEvent(ClientEvent ce) throws Exception {
        System.out.println("LoadingController got message from client.");
        try {
            this.next = ce.getNextViewName();
            Platform.runLater(
                    () -> {
                        try {
                            loadNextPhase((Stage) loadingMessage.getScene().getWindow());
                        } catch (IOException e) {
                            loadingMessage.setText(e.getMessage());
                        }
                    });
        } catch (Exception e) {
            System.out.println("Exception in LoadingController's onUpdateEvent: " + e.getMessage());
            throw new Exception(e.getMessage());
        }
    }
}
