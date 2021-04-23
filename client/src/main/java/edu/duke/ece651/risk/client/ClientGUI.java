package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.client.controller.ControllerFactory;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application {
    Stage window;
    App model;
    String next = "serverConnect";

    /**
     * Shows the splash screen.
     */
    @Override
    public void init() throws InterruptedException {
        Double DURATION_SECONDS = 1.5; // duration of splash screen
        Thread.sleep((long) (1000 * DURATION_SECONDS));
    }

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        model = new App();
        Thread t=new Thread(model);
        t.start();
        Object controller = new ControllerFactory().getController(next, model);
        window = PhaseChanger.switchTo(window, controller, next);
        window.getScene().getStylesheets().add(getClass().getResource("/ui/styling/fonts.css").toString());
        window.show();
    }

    /** Loads the splash screen followed by the the GUI app.
     * @param args
     */
    public static void main(String[] args) {
        System.setProperty("javafx.preloader", SplashScreen.class.getCanonicalName());
        launch(args);
    }
}








