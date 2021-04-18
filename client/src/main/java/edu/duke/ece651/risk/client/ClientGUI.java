package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.client.controller.ControllerFactory;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application {
    Stage window;
    App model;
    String next = "serverConnect";

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
}








