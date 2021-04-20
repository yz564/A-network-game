package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class AllocateTalentsController extends Controller
        implements Initializable, ErrorHandlingController {
    WorldMap map;
    int numUnitsEntered;

    @FXML ImageView mapImageView;
    @FXML Circle charSelected;
    @FXML Label nameSelected;
    @FXML Label numAllocated;
    @FXML Label numAllowed;
    @FXML Label errorMessage;
    @FXML ArrayList<Label> labelList;
    @FXML ArrayList<TextField> numList;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public AllocateTalentsController(App model) {
        super(model);
        this.numUnitsEntered = 0;
        this.next = "loading";
    }

    /**
     * Sets various elements in the view to default values.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get map from client App
        this.map = model.getPlayer().getMap();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set map image according to number of players
        helper.initializeMap(map, mapImageView);
        // set coloring for each territory label
        helper.initializeTerritoryLabelByGroup(model.getPlayer().getMap(), labelList);
        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(model.getPlayer().getMap(), labelList);
        // set image and label for selected character
        helper.initializeSelectedCharacter(model, charSelected, nameSelected);
        // set visibility of numUnits fields according to selected territory group
        helper.initializeNumUnitsFields(model, labelList, numList);
        // set number of units allowed
        numAllowed.setText(String.valueOf(model.getPlayer().getMaxUnitsToPlace()));
        // add change listeners to numUnits fields
        addNumUnitsChangeListeners();
        /*
        try {
            model.getPlayer().startAllocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
         */
    }

    private void addNumUnitsChangeListeners() {
        for (TextField numField : numList) {
            numField.textProperty()
                    .addListener(
                            (observable, oldValue, newValue) -> {
                                try {
                                    clearErrorMessage();
                                    updateTotalUnitsAllocated();
                                } catch (IllegalArgumentException e) {
                                    setErrorMessage(e.getMessage());
                                }
                            });
        }
    }

    /**
     * Returns an integer from text.
     *
     * @param text is the string from which integer is parsed.
     */
    private int parsePosIntFromTextField(String text) throws IllegalArgumentException {
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

    private void updateTotalUnitsAllocated() throws IllegalArgumentException {
        int TotalUnits = calculateTotalUnitsAllocated();
        numAllocated.setText(String.valueOf(TotalUnits));
        if (TotalUnits > Integer.valueOf(numAllowed.getText())) {
            numAllocated.setTextFill(Color.HOTPINK);
            throw new IllegalArgumentException(
                    "Invalid input: Total number of talents deployed is more than maximum allowed.");
        } else {
            numAllocated.setTextFill(Color.WHITE);
        }
    }

    private int calculateTotalUnitsAllocated() throws IllegalArgumentException {
        int totalUnits = 0;
        for (TextField numField : numList) {
            int id = numList.indexOf(numField);
            StyleMapping mapping = new StyleMapping();
            String territoryName = mapping.getTerritoryName(labelList.get(id).getId());
            try {
                totalUnits += parsePosIntFromTextField(numField.getText());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Invalid input in " + territoryName + " : " + e.getMessage());
            }
        }
        return totalUnits;
    }

    /** Asks the server to allocate the specified number of units from the view. */
    public void onAllocate(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            try {
                clearErrorMessage();
                updateTotalUnitsAllocated();
                String allocate = model.getPlayer().tryAllocation(this.getTerritoryUnits());
                if (allocate != null) {
                    setErrorMessage(allocate);
                } else {
                    loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
                }
            } catch (IllegalArgumentException e) {
                setErrorMessage(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /**
     * Returns a map of territory names to the number of units requested to deploy in the territory
     * names.
     */
    HashMap<String, Integer> getTerritoryUnits() {
        HashMap<String, Integer> territoryUnits = new HashMap<>();
        for (TextField numField : numList) {
            int id = numList.indexOf(numField);
            if (map.getNumPlayers() % 2 == 1 && id == 15) {
                continue;
            }
            StyleMapping mapping = new StyleMapping();
            String territoryName = mapping.getTerritoryName(labelList.get(id).getId());
            if (map.getTerritory(territoryName).isBelongTo(model.getPlayer().getName())) {
                territoryUnits.put(territoryName, parsePosIntFromTextField(numField.getText()));
            }
        }
        return territoryUnits;
    }

    @Override
    public void setErrorMessage(String error) {
        errorMessage.setText(error);
    }

    @Override
    public void clearErrorMessage() {
        setErrorMessage(null);
    }
}
