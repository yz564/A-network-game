package edu.duke.ece651.risk.client;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SplashScreen extends Preloader {
    private Stage preloadStage;
    private Scene preloadScene;

    /**
     * Empty constructor.
     */
    public SplashScreen () {}

    /**
     * Initialize scene. This method runs right before start().
     */
    @Override
    public void init() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/ui/views/splash.fxml"));
        preloadScene = new Scene(root);
    }

    /**
     * Set the stage for the splash screen.
     */
    @Override
    public void start(Stage stage) {
        preloadStage = stage;
        preloadStage.setScene(preloadScene);
        preloadStage.show();
    }

    /**
     * Hides the splash screen.
     * @param info tells that the ClientGUI.init() has finished execution.
     */
    @Override
    public void handleStateChangeNotification(Preloader.StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                // Executed right after ClientGUI.init() finishes and before ClientGUI.start() starts execution.
                preloadStage.hide();
                break;
        }
    }
}
