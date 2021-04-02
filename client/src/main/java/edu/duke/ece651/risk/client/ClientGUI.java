package edu.duke.ece651.risk.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;

public class ClientGUI extends Application {
    Stage window;
    Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;

        URL xmlResource = getClass().getResource("/ui/views/server-connect.fxml");
        Pane p = FXMLLoader.load(xmlResource);
        scene = new Scene(p);

        window.setScene(scene);
        window.setTitle("Duke Risk Game! - Connect Server");
        window.show();
    }
}
