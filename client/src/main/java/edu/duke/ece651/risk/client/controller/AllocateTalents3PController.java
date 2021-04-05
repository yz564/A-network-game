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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ResourceBundle;

public class AllocateTalents3PController implements Initializable {
    App model;
    String next;

    // territory name for which player will enter number of talents to deploy
    @FXML
    Label territory1Label;

    @FXML
    Label territory2Label;

    @FXML
    Label territory3Label;

    @FXML
    Label territory4Label;

    @FXML
    Label territory5Label;

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

    // label to printing error messages to the player
    @FXML
    Label errorMessage;

    public AllocateTalents3PController(App model) {
        this.model = model;
        this.next = "test";
    }

    public void initialize(URL location, ResourceBundle resources) {
        setTerritoryLabels();
    }

    /* Sets the territory names in FXML that a player will see while entering number of talents.
     */
    private void setTerritoryLabels() {
        Player currentPlayer = model.getPlayer();
        WorldMap map = currentPlayer.getMap();
        HashMap<String, Territory> playerTerritories = map.getPlayerTerritories(currentPlayer.getName());
        Iterator<String> itr = playerTerritories.keySet().iterator();
        territory1Label.setText(itr.next());
        territory2Label.setText(itr.next());
        territory3Label.setText(itr.next());
        territory4Label.setText(itr.next());
        territory5Label.setText(itr.next());
    }

    /* Asks the server to allocate the specified number of units from the view.
     */
    public void onAllocate(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            String allocate = model.getPlayer().tryAllocation(this.getTerritoryUnits());
            if (allocate != null) {
                errorMessage.setText("Total number of units deployed (" +
                        getNumUnitsRequested() +
                        ") exceed the number of units that you have.");
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
        int parsedInt;
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
        HashMap<String, Integer> territoryUnits;
        territoryUnits.put(territory1Label.getText(), parseIntFromTextField(numTalent1, 1));
        territoryUnits.put(territory2Label.getText(), parseIntFromTextField(numTalent2, 2));
        territoryUnits.put(territory3Label.getText(), parseIntFromTextField(numTalent3, 3));
        territoryUnits.put(territory4Label.getText(), parseIntFromTextField(numTalent4, 4));
        territoryUnits.put(territory5Label.getText(), parseIntFromTextField(numTalent5, 5));
        return territoryUnits;
    }

    /* Returns the total number of units requested by the player in the view.
     */
    int getNumUnitsRequested() {
        return parseIntFromTextField(numTalent1, 1) +
                parseIntFromTextField(numTalent2, 2) +
                parseIntFromTextField(numTalent3, 3) +
                parseIntFromTextField(numTalent4, 4) +
                parseIntFromTextField(numTalent5, 5);
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
