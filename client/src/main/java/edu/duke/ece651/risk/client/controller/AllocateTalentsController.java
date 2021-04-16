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

public class AllocateTalentsController extends Controller implements Initializable, ErrorHandlingController {
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
     * @param model is the backend of the game.
     */
    public AllocateTalentsController(App model) {
        super(model);
        this.numUnitsEntered = 0;
        this.next = "selectAction";
    }

    /**
     * Sets various elements in the view to default values.
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
        try {
            model.getPlayer().startAllocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNumUnitsChangeListeners(){
        for (TextField numField: numList){
            numField.textProperty().addListener((observable, oldValue, newValue) -> {
                updateTotalUnitsAllocated();
            });
        }
    }

    /**
     * Returns an integer from text.
     * @param text is the string from which integer is parsed.
     */
    private int parsePosIntFromTextField(String text) throws NumberFormatException {
        int parsedInt = 0;
        parsedInt = Integer.parseInt(text);
        if (parsedInt < 0){
            throw new NumberFormatException();
        }
        return parsedInt;
    }

    private void updateTotalUnitsAllocated(){
        int TotalUnits = returnTotalUnitsAllocated();
        if (TotalUnits > Integer.valueOf(numAllowed.getText())){
            numAllocated.setTextFill(Color.HOTPINK);
            setErrorMessage("Invalid input: Total number of talents deployed is more than maximum allowed. Please reduce number deployed in one or more territory.");
        }
        else{
            numAllocated.setTextFill(Color.WHITE);
        }
        numAllocated.setText(String.valueOf(TotalUnits));
    }

    /**
     * Returns the total number of units requested by the player in the view.
     */
    private int returnTotalUnitsAllocated() {
        clearErrorMessage();
        int totalUnits = 0;
        for (TextField numField: numList) {
            int id = numList.indexOf(numField);
            StyleMapping mapping = new StyleMapping();
            String territoryName = mapping.getTerritoryName(labelList.get(id).getId());
            try {
                totalUnits += parsePosIntFromTextField(numField.getText());
            }
            catch (Exception e){
                setErrorMessage("Invalid input: Please enter a positive integer number of talents in " + territoryName);
            }
        }
        return totalUnits;
    }

    /**
     * Asks the server to allocate the specified number of units from the view.
     */
    public void onAllocate(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            updateTotalUnitsAllocated();
            String allocate = model.getPlayer().tryAllocation(this.getTerritoryUnits());
            if (allocate != null) {
                setErrorMessage(allocate);
            }
            else {
                loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
            }
        }
        else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }


    /**
     * Returns a map of territory names to the number of units requested to deploy in the territory names.
     */
    HashMap<String, Integer> getTerritoryUnits() {
        HashMap<String, Integer> territoryUnits = new HashMap<>();
        for (TextField numField : numList){
            int id = numList.indexOf(numField);
            if (map.getNumPlayers() % 2 == 1 && id == 15){
                continue;
            }
            StyleMapping mapping = new StyleMapping();
            String territoryName = mapping.getTerritoryName(labelList.get(id).getId());
            territoryUnits.put(territoryName,
                    parsePosIntFromTextField(numField.getText()));
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

