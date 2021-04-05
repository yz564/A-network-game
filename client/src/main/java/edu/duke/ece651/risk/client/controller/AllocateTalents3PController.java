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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AllocateTalents3PController implements Initializable {
    App model;
    String next;
    int numUnitsEntered;

    // territory name for which player will enter number of talents to deploy
    @FXML
    Label talentLabel1;

    @FXML
    Label talentLabel2;

    @FXML
    Label talentLabel3;

    @FXML
    Label talentLabel4;

    @FXML
    Label talentLabel5;

    // number of talents to deploy in each territory
    @FXML
    TextField numTalent1;

    @FXML
    TextField numTalent2;

    @FXML
    TextField numTalent3;

    @FXML
    TextField numTalent4;

    @FXML
    TextField numTalent5;

    // allocation prompt that asks to enter the number of talents.
    @FXML
    Label allocatePrompt;

    @FXML
    Label numUnitsAllocated;

    @FXML
    Label numUnitsAllowed;

    @FXML
    Label territoryGroupName;

    // label for printing error messages and acknowledgement messages to the player.
    @FXML
    Label errorMessage;

    public AllocateTalents3PController(App model) {
        this.model = model;
        this.numUnitsEntered = 0;
        this.next = "test";
    }

    public void initialize(URL location, ResourceBundle resources) {
        setTerritoryNameLabels();
        territoryGroupName.setText(model.getPlayer().getTerritorySelected());
        numTalent1.setText("0");
        numTalent2.setText("0");
        numTalent3.setText("0");
        numTalent4.setText("0");
        numTalent5.setText("0");
        numUnitsAllocated.setText(String.valueOf(0));
        numUnitsAllowed.setText(String.valueOf(model.getPlayer().getMaxUnitsToPlace()));
    }

    /* Sets the territory names in FXML that a player will see while entering number of talents.
     */
    private void setTerritoryNameLabels() {
        Player currentPlayer = model.getPlayer();
        WorldMap map = currentPlayer.getMap();
        HashMap<String, Territory> playerTerritories = map.getPlayerTerritories(currentPlayer.getName());
        Iterator<String> itr = playerTerritories.keySet().iterator();
        talentLabel1.setText(itr.next());
        talentLabel2.setText(itr.next());
        talentLabel3.setText(itr.next());
        talentLabel4.setText(itr.next());
        talentLabel5.setText(itr.next());
    }

    /* Updates the view for total number of units allocated so far while typing.
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
        if (numKeys.contains(source)) {
            numUnitsAllocated.setText(String.valueOf(getNumUnitsRequested()));
        }
        else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /* Asks the server to allocate the specified number of units from the view.
     */
    public void onAllocate(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            numUnitsAllocated.setText(String.valueOf(this.getNumUnitsRequested()));
            String allocate = model.getPlayer().tryAllocation(this.getTerritoryUnits());
            if (allocate != null) {
                errorMessage.setText(allocate);
            }
            else {
                loadNextPhase(ae);
            }
        }
        else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    /* Returns an integer from text.
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

    /* Returns a map of territory names to the number of units requested to deploy in the territory names.
     */
    HashMap<String, Integer> getTerritoryUnits() {
        HashMap<String, Integer> territoryUnits = new HashMap<>();
        territoryUnits.put(talentLabel1.getText(), parseIntFromTextField(numTalent1.getText(), 1));
        territoryUnits.put(talentLabel2.getText(), parseIntFromTextField(numTalent2.getText(), 2));
        territoryUnits.put(talentLabel3.getText(), parseIntFromTextField(numTalent3.getText(), 3));
        territoryUnits.put(talentLabel4.getText(), parseIntFromTextField(numTalent4.getText(), 4));
        territoryUnits.put(talentLabel5.getText(), parseIntFromTextField(numTalent5.getText(), 5));
        return territoryUnits;
    }

    /* Returns the total number of units requested by the player in the view.
     */
    int getNumUnitsRequested() {
        return parseIntFromTextField(numTalent1.getText(), 1) +
                parseIntFromTextField(numTalent2.getText(), 2) +
                parseIntFromTextField(numTalent3.getText(), 3) +
                parseIntFromTextField(numTalent4.getText(), 4) +
                parseIntFromTextField(numTalent5.getText(), 5);
    }

    /* Loads the next Phase.
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
