package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.client.controller.ControllerFactory;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application {
    Stage window;
    Object model;
    String next = "serverConnect";

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        model = new App();
        Object controller = new ControllerFactory().getController(next, model);
        window = PhaseChanger.switchTo(window, controller, next);
        window.show();
    }
}
