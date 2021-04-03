package edu.duke.ece651.risk.client.view;

import javafx.stage.Stage;

import java.io.IOException;

public class WindowChanger {

    public static Stage switchTo(Stage window, Object controller, String phaseName) throws IOException {
        //Stage window = (Stage) (((Node) source).getScene().getWindow());
        Phase newPhase = PhaseStorage.getPhase(phaseName);
        if (newPhase != null) {
            window.setScene(newPhase.getView().makeScene(controller));
            window.setTitle(newPhase.getWindowTitle());
            return window;
        }
        else {
            return null;
        }
    }
}
