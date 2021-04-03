package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.client.controller.ControllerFactory;
import edu.duke.ece651.risk.client.view.WindowChanger;
import javafx.application.Application;
import javafx.stage.Stage;

public class ClientGUI extends Application {
    Stage window;
    Object model;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        model = new App();
        Object controller = new ControllerFactory().getController("serverConnect", model);
        window = WindowChanger.switchTo(window, controller, "serverConnect");
        window.show();
    }
}
