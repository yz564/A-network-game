package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ActionInfoFactory;
import edu.duke.ece651.risk.shared.Territory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UpgradeTalentsActionEvenPlayersController implements Initializable {
    App model;
    String next;
    ObservableList territoryNames;
    ObservableList talentNames;
    HashMap<String, String> talents;

    @FXML
    ChoiceBox sourceTerritoryName;

    @FXML
    Label techLevel;

    @FXML
    ChoiceBox sourceTalentName;

    @FXML
    ChoiceBox destTalentName;

    @FXML
    TextField numTalents;

    @FXML
    Label errorMessage;

    @FXML
    Label territoryGroupName;

    public UpgradeTalentsActionEvenPlayersController(App model) {
        this.model = model;
        this.next = "selectActionEvenPlayers";
        territoryNames = FXCollections.observableArrayList();
        talentNames = FXCollections.observableArrayList();
        talents = new HashMap<>();
        talents.put("Undergrad", "level0");
        talents.put("Masters", "level1");
        talents.put("PhD", "level2");
        talents.put("Postdoc", "level3");
        talents.put("Assistant Professor", "level4");
        talents.put("Associate Professor", "level5");
        talents.put("Professor", "level6");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        territoryGroupName.setText("Upgrade Talents");
        setSourceTerritoryNames();
        String playerName = model.getPlayer().getName();
        techLevel.setText(String.valueOf(model.getPlayer().getMap().getPlayerInfo(playerName).getTechLevel()));
        setUpgradeChoiceBox(sourceTalentName);
        setUpgradeChoiceBox(destTalentName);
    }

    /* Fills the choice boxes with a list of territories that a player owns.
     * After the choice boxes are filled, the player is able to select the source
     * territory for attacking.
     */
    private void setSourceTerritoryNames() {
        territoryNames.removeAll(territoryNames);
        String playerName = model.getPlayer().getName();
        HashMap<String, Territory> playerTerritories = model.getPlayer().getMap().getPlayerTerritories(playerName);
        for (String territoryName: playerTerritories.keySet()) {
            territoryNames.add(territoryName);
        }
        sourceTerritoryName.getItems().addAll(territoryNames);
    }

    /* Populates a choice box with the names of various talents present in the game such as 'Undergrad', 'Masters', etc.
     */
    private void setUpgradeChoiceBox(ChoiceBox box) {
        talentNames.removeAll(talentNames);
        for (String name: talents.keySet()) {
            talentNames.add(name);
        }
        box.getItems().addAll(talentNames);
    }

    /* Sends upgrade talent request to the server. Switches the view back to Action Selection view if successful,
     * or displays an error message.
     */
    @FXML
    public void onUpgradeTalent(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            String validate = validateAction();
            if (validate != null) {
                errorMessage.setText(validate);
            }
            else {
                ActionInfoFactory af = new ActionInfoFactory();
                ActionInfo info = af.createUpgradeUnitActionInfo(model.getPlayer().getName(),
                        (String) sourceTerritoryName.getValue(),
                        talents.get((String) sourceTalentName.getValue()),
                        talents.get((String) destTalentName.getValue()),
                        parseIntFromTextField(numTalents.getText()));
                String success = model.getPlayer().tryIssueUpgradeUnitOrder(info);
                if (success != null) {
                    errorMessage.setText(success);
                }
                else {
                    loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
                }
            }
        }
        else {
            throw new IllegalArgumentException("Invalid source " + ae.getSource() + " for this action.");
        }
    }

    public void onTypingNumUnits(KeyEvent ke) throws Exception {
    }

    /* Ensures all the inputs through various buttons, textfields, choice boxes, etc are valid for creating a
     * ActionInfo object for upgrading tech talent. Returns null is choices are valid, else a string describing error.
     */
    private String validateAction() {
        try {
            validateChoiceBox(sourceTerritoryName);
            validateChoiceBox(sourceTalentName);
            validateChoiceBox(destTalentName);
            parseIntFromTextField(numTalents.getText());
        }
        catch (Exception e) {
            return e.getMessage();
        }
        return null;
    }

    /* Returns a string with an error description if ChoiceBox has null value, else returns a null string.
     */
    private String validateChoiceBox(ChoiceBox box) {
        String error = box + " cannot be empty.";
        try {
            if (((String) box.getValue()).equals("") || ((String) box.getValue()) == null) {
                throw new IllegalArgumentException(error);
            }
        }
        catch (IllegalArgumentException iae) {
            throw new IllegalArgumentException(error);
        }
        return null;
    }

    /* Returns an integer from text.
     * @param text is the string from which integer is parsed.
     * @param itemNumber is the index of the TextField. There are five TextField items 1, 2, 3, 4 and 5.
     *        itemNumber is used for printing error messages if parsing fails.
     */
    private int parseIntFromTextField(String text) throws IllegalArgumentException {
        int parsedInt = 0;
        try {
            parsedInt = Integer.parseInt(text);
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Integer cannot be parsed from " + text);
        }
        return parsedInt;
    }

    /* Loads next phase after a player clicks on a menu item inside a SplitMenuButton.
     * @param window is the source Stage where the user interacted.
     */
    private void loadNextPhase(Stage window) throws IOException {
        Object controller = new ControllerFactory().getController(next, model);
        Stage newWindow = PhaseChanger.switchTo(window, controller, next);
        newWindow.show();
    }
}
