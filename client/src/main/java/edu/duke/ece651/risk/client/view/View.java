package edu.duke.ece651.risk.client.view;

import edu.duke.ece651.risk.client.controller.ServerConnectController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class View {
    private final String name;
    private final String xmlPath;
    private final String cssPath;

    public View (String name, String xmlPath, String cssPath) {
        this.name = name;
        this.xmlPath = xmlPath;
        this.cssPath = cssPath;
    }

    public View(String name, String xmlPath) {
        this(name, xmlPath, null);
    }

    public String getName() {
        return this.name;
    }

    public String getXMLPath() {
        return this.xmlPath;
    }

    public String getCSSPath() {
        return this.cssPath;
    }

    public String toString() {
        return name + "view.\n" + "XML Path: `" + xmlPath + "`\n";
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public Scene makeScene(Object controller) throws IOException {
        URL xmlResource = getClass().getResource(this.xmlPath);
        FXMLLoader loader = new FXMLLoader(xmlResource);
        loader.setControllerFactory((c) ->{return controller;});
        Pane p = loader.load();
        return new Scene(p);
    }
}
