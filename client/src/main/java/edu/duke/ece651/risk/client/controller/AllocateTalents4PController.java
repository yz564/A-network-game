package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.Player;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AllocateTalents4PController implements Initializable {
    App model;
    String next;
    int numUnitsEntered;

    @FXML ArrayList<Label> talentLabelList;

    @FXML  ArrayList<TextField> numTalentList;

    @FXML Label allocatePrompt;

    @FXML Label numUnitsAllocated;

    @FXML Label numUnitsAllowed;

    @FXML Label territoryGroupName;

    @FXML Label errorMessage;

    @FXML ArrayList<Label> labelList;

    /**
     * Constructor that initializes the model.
     * @param model is the backend of the game.
     */
    public AllocateTalents4PController(App model) {
        this.model = model;
        this.numUnitsEntered = 0;
        this.next = "selectActionEvenPlayers";
    }

    /**
     * Sets various elements in the view to default values.
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    public void initialize(URL location, ResourceBundle resources) {
        InitializeControllerHelper helper = new InitializeControllerHelper();

        // set coloring for each territory label
        helper.initializeTerritoryLabelByGroup(model.getPlayer().getMap(), labelList);

        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(model.getPlayer().getMap(), labelList);

        // set player color
        helper.initializeTerritoryGroupLabelColor(model, territoryGroupName);

        // set territory names for this type of territory group
        setTerritoryNameLabels();

        // set number of talents to 0
        for (TextField numTalent: numTalentList) {
            numTalent.setText("0");
        }

        numUnitsAllocated.setText(String.valueOf(0));
        numUnitsAllowed.setText(String.valueOf(model.getPlayer().getMaxUnitsToPlace()));

        try {
            model.getPlayer().startAllocation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Sets the territory names in FXML that a player will see while entering number of talents.
     */
    private void setTerritoryNameLabels() {
        Player currentPlayer = model.getPlayer();
        WorldMap map = currentPlayer.getMap();
        HashMap<String, Territory> playerTerritories = map.getPlayerTerritories(currentPlayer.getName());
        Iterator<String> itr = playerTerritories.keySet().iterator();
        for (Label talentName: talentLabelList) {
            talentName.setText(itr.next());
        }
    }

    /**
     * Updates the view for total number of units allocated so far while typing.
     */
    public void onTypingNumUnits(KeyEvent ke) {
        Object source = ke.getCode();
        HashSet<KeyCode> numKeys = new HashSet<>();
        numKeys.add(KeyCode.DIGIT0);
        numKeys.add(KeyCode.DIGIT1);
        numKeys.add(KeyCode.DIGIT2);
        numKeys.add(KeyCode.DIGIT3);
        numKeys.add(KeyCode.DIGIT4);
        numKeys.add(KeyCode.DIGIT5);
        numKeys.add(KeyCode.DIGIT6);
        numKeys.add(KeyCode.DIGIT7);
        numKeys.add(KeyCode.DIGIT8);
        numKeys.add(KeyCode.DIGIT9);
        numKeys.add(KeyCode.BACK_SPACE);
        numKeys.add(KeyCode.TAB);
        if (numKeys.contains(source)) {
            numUnitsAllocated.setText(String.valueOf(getNumUnitsRequested()));
        }
        else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /**
     * Asks the server to allocate the specified number of units from the view.
     */
    public void onAllocate(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            String isValidAllocation = checkInput();
            if (isValidAllocation != null) {
                errorMessage.setText(isValidAllocation);
            }
            else {
                numUnitsAllocated.setText(String.valueOf(this.getNumUnitsRequested()));
                String allocate = model.getPlayer().tryAllocation(this.getTerritoryUnits());
                if (allocate != null) {
                    errorMessage.setText(allocate);
                }
                else {
                    loadNextPhase(ae);
                }
            }
        }
        else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /**
     * Ensures all the number of talents are positive
     * Returns a string with a descriptive error if there are negative number of units, else returns null.
     */
    private String checkInput() {
        for (TextField numTalent: numTalentList) {
            if (parseIntFromTextField(numTalent.getText(), numTalentList.indexOf(numTalent) + 1) < 0) {
                return "Invalid input. Cannot allocate negative talents.";
            }
        }
        return null;
    }

    /**
     * Returns an integer from text.
     * @param text is the string from which integer is parsed.
     * @param itemNumber is the index of the TextField. There are five TextField items 1, 2, 3, 4 and 5.
     *        itemNumber is used for printing error messages if parsing fails.
     */
    private int parseIntFromTextField(String text, int itemNumber) {
        int parsedInt = 0;
        try {
            parsedInt = Integer.parseInt(text);
        }
        catch (Exception e) {
            errorMessage.setText("Please enter an integer in box number " + itemNumber);
        }
        return parsedInt;
    }

    /**
     * Returns a map of territory names to the number of units requested to deploy in the territory names.
     */
    HashMap<String, Integer> getTerritoryUnits() {
        HashMap<String, Integer> territoryUnits = new HashMap<>();
        Iterator<Label> talentNameItr = talentLabelList.iterator();
        Iterator<TextField> numTalentItr = numTalentList.iterator();
        Label currTalentName;
        TextField currNumTalents;
        while(talentNameItr.hasNext() && numTalentItr.hasNext()) {
            currTalentName = talentNameItr.next();
            currNumTalents = numTalentItr.next();
            territoryUnits.put(currTalentName.getText(),
                    parseIntFromTextField(currNumTalents.getText(), numTalentList.indexOf(currNumTalents) + 1));
        }
        return territoryUnits;
    }

    /**
     * Returns the total number of units requested by the player in the view.
     */
    int getNumUnitsRequested() {
        int totalUnits = 0;
        for (TextField numTalents: numTalentList) {
            totalUnits += parseIntFromTextField(numTalents.getText(), numTalentList.indexOf(numTalents) + 1);
        }
        return totalUnits;
    }

    /**
     * Loads the next Phase.
     * @param ae is used to compute the parent of the item that interacted
     * with the view that this controller is attached to.
     */
    private void loadNextPhase(ActionEvent ae) throws IOException {
        Stage window = (Stage) (((Node) ae.getSource()).getScene().getWindow());
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }
}
