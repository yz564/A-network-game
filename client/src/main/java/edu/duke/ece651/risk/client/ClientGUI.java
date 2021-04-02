package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.client.controller.ServerConnectController;
import edu.duke.ece651.risk.client.controller.UserLoginController;
import edu.duke.ece651.risk.shared.ObjectIO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;

public class ClientGUI extends Application {
    Stage window;
    Scene scene;
    App model;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        model = new App();
        Thread t = new Thread(model);
        t.start();

        URL xmlResource = getClass().getResource("/ui/views/user-login.fxml");
        FXMLLoader loader = new FXMLLoader(xmlResource);
        loader.setControllerFactory(c -> {
            return new UserLoginController(model);
        });
        Pane p = loader.load();
        scene = new Scene(p);

        window.setScene(scene);
        window.setTitle("Duke Risk Game! - Log In");
        window.show();
    }
}
