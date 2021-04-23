package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public abstract class ActionController extends Controller {
    WorldMap map;
    String playerName;
    String srcTerritoryName;
    String destTerritoryName;
    Stage mainPage;

    @FXML GridPane grid;
    @FXML Label srcName;
    @FXML Label destName;
    @FXML ImageView srcImage;
    @FXML ImageView destImage;
    @FXML Label resourceAvailable;
    @FXML Label actionCost;
    @FXML ArrayList<TextField> numList;
    @FXML ArrayList<Slider> sliderList;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public ActionController(App model, String srcName, String destName, Stage mainPage) {
        super(model);
        this.next = "selectAction";
        this.playerName = model.getPlayer().getName();
        this.srcTerritoryName = srcName;
        this.destTerritoryName = destName;
        this.mainPage = mainPage;
        this.map = model.getPlayer().getMap();
    }

    /**
     * Initializes the view.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    protected void loadResourceInfo(String resource) {
        resourceAvailable.setText(
                String.valueOf(
                        map.getPlayerInfo(model.getPlayer().getName())
                                .getResTotals()
                                .get(resource)));
    }

    protected void loadTerritoryInfo() {
        if (srcTerritoryName != null) {
            srcName.setText(srcTerritoryName);
            srcImage.setImage(new Image("ui/static-images/territory/" + srcTerritoryName + ".jpg"));
        }
        if (destTerritoryName != null) {
            destName.setText(destTerritoryName);
            destImage.setImage(
                    new Image("ui/static-images/territory/" + destTerritoryName + ".jpg"));
        }
    }

    protected void addNumUnitsChangeListeners(String resource) {
        for (TextField numField : numList) {
            numField.textProperty()
                    .addListener(
                            (observable, oldValue, newValue) -> {
                                try {
                                    clearErrorMessage();
                                    updateTotalCost(getActionInfo(), resource);
                                } catch (IllegalArgumentException e) {
                                    setErrorMessage(e.getMessage());
                                }
                            });
        }
    }

    protected void addSliderChangeListener() {
        for (Slider numSlider : sliderList) {
            int id = sliderList.indexOf(numSlider);
            numSlider
                    .valueProperty()
                    .addListener(
                            (observable, oldValue, newValue) -> {
                                numList.get(id)
                                        .textProperty()
                                        .setValue(String.valueOf(newValue.intValue()));
                            });
        }
    }

    /**
     * Triggered when a player hits the cancel button. Player is taken back to the select action
     * window.
     */
    @FXML
    public void onCancel(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if (source instanceof Button) {
            Stage popup = (Stage) (((Node) ae.getSource()).getScene().getWindow());
            popup.close();
            // loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
        } else {
            throw new IllegalArgumentException(
                    "Invalid source " + source + " for the cancel upgrade tech level method.");
        }
    }

    /**
     * Returns an integer from text.
     *
     * @param text is the string from which integer is parsed.
     */
    protected int parsePosIntFromTextField(String text) throws IllegalArgumentException {
        int parsedInt = 0;
        try {
            parsedInt = Integer.parseInt(text);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Number of talents deployed must be an integer.");
        }
        if (parsedInt < 0) {
            throw new IllegalArgumentException("Number of talents deployed cannot be negative.");
        }
        return parsedInt;
    }

    protected void updateTotalCost(ActionInfo actionInfo, String resource)
            throws IllegalArgumentException {
        int TotalCost = calculateTotalCost(actionInfo, resource);
        actionCost.setText(String.valueOf(TotalCost));
        if (TotalCost > Integer.parseInt(resourceAvailable.getText())) {
            actionCost.setTextFill(Color.HOTPINK);
            throw new IllegalArgumentException(
                    "Invalid action: Total action cost is more than resource available.");
        } else if (TotalCost == 0) {
            actionCost.setTextFill(Color.HOTPINK);
            throw new IllegalArgumentException(
                    "Invalid action: Action cannot be empty with zero action cost.");
        } else {
            actionCost.setTextFill(Color.WHITE);
        }
    }

    protected int calculateTotalCost(ActionInfo actionInfo, String resource) {
        ActionCostCalculator calc = new ActionCostCalculator();
        HashMap<String, Integer> costs = calc.calculateCost(actionInfo, map);
        return costs.get(resource);
    }

    /** Returns map of troop names to the number of units requested to perform the action */
    protected HashMap<String, Integer> getNumUnits() {
        HashMap<String, Integer> numUnits = new HashMap<>();
        for (TextField numField : numList) {
            int id = numList.indexOf(numField);
            numUnits.put("level" + id, parsePosIntFromTextField(numField.getText()));
        }
        return numUnits;
    }

    protected ActionInfo getActionInfo() throws IllegalArgumentException {
        return null;
    }
}
