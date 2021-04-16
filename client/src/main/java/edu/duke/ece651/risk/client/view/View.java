package edu.duke.ece651.risk.client.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class View {
    private final String name;
    private final String xmlPath;
    private ArrayList<String> cssPathList = new ArrayList<>();

    public View (String name, String xmlPath, String... cssPaths) {
        this.name = name;
        this.xmlPath = xmlPath;
        if (cssPaths != null) {
            for (String cssPath : cssPaths) {
                cssPathList.add(cssPath);
            }
        }
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

    public ArrayList<String> getCSSPath() {
        return this.cssPathList;
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
        //Pane p = loader.load();
        Scene scene = new Scene(loader.load());
        if (!cssPathList.isEmpty()) {
            for (String cssPath : cssPathList) {
                URL cssResource = getClass().getResource(cssPath);
                scene.getStylesheets().add(cssResource.toString());
            }
        }
        return scene;
    }

}