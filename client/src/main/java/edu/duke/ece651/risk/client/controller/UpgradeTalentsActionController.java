package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ActionInfoFactory;
import edu.duke.ece651.risk.shared.ActionRuleCheckerHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class UpgradeTalentsActionController extends ActionController {
    ObservableList<String> upgradeFrom = FXCollections.observableArrayList();
    ObservableList<String> upgradeTo = FXCollections.observableArrayList();
    String oldUnitName;
    String newUnitName;

    @FXML ChoiceBox<String> oldTalent;
    @FXML ChoiceBox<String> newTalent;
    @FXML ImageView oldTalentImage;
    @FXML ImageView newTalentImage;
    @FXML Label oldTalentLabel;
    @FXML Label newTalentLabel;
    @FXML Label oldTalentNum;
    @FXML Label newTalentNum;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public UpgradeTalentsActionController(App model, String srcName, Stage mainPage) {
        super(model, srcName, null, mainPage);
    }

    /**
     * Sets various elements in the view to default values.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadTerritoryInfo();
        loadResourceInfo("tech");
        setUpgradeFromChoiceBox();
        addChoiceBoxChangeListener();
        addSliderChangeListener();
        addNumUnitsChangeListeners("tech");
    }

    private void addChoiceBoxChangeListener() {
        StyleMapping mapping = new StyleMapping();
        oldTalent
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            try {
                                clearErrorMessage();
                                String oldTalentName = upgradeFrom.get((Integer) newValue);
                                oldUnitName = mapping.getTalents(oldTalentName);
                                setNum(oldUnitName, oldTalentNum);
                                setImageLabel(
                                        oldTalentName, oldUnitName, oldTalentLabel, oldTalentImage);
                                setUpgradeToChoiceBox(oldTalentName);
                                InitializeControllerHelper helper = new InitializeControllerHelper();
                                // set the max value for sliders for each talent types
                                helper.initializeSliders(
                                        sliderList,
                                        new ArrayList<Label>() {
                                            {
                                                add(oldTalentNum);
                                            }
                                        });
                            } catch (IllegalArgumentException e) {
                                setErrorMessage(e.getMessage());
                            }
                        });
        newTalent
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(
                        (observable, oldValue, newValue) -> {
                            try {
                                clearErrorMessage();
                                String newTalentName = upgradeTo.get((Integer) newValue);
                                newUnitName = mapping.getTalents(newTalentName);
                                setNum(newUnitName, newTalentNum);
                                setImageLabel(
                                        newTalentName, newUnitName, newTalentLabel, newTalentImage);
                            } catch (IllegalArgumentException e) {
                                setErrorMessage(e.getMessage());
                            }
                        });
    }

    /** Sets the choice box with units that a player could upgrade FROM. */
    private void setUpgradeFromChoiceBox() {
        StyleMapping mapping = new StyleMapping();
        oldTalent.getItems().removeAll(upgradeFrom);
        upgradeFrom.clear();
        for (String talentName : mapping.getTalentNames()) {
            String unitName = mapping.getTalents(talentName);
            int numUnit = map.getTerritory(srcTerritoryName).getTroopNumUnits(unitName);
            if (numUnit > 0) {
                upgradeFrom.add(talentName);
            }
        }
        oldTalent.getItems().addAll(upgradeFrom);
    }

    private void setImageLabel(
            String talentName, String unitName, Label talentLabel, ImageView talentImage) {
        talentLabel.setText(talentName);
        talentImage.setImage(new Image("ui/icons/tooltip/" + unitName + ".png"));
    }

    private void setNum(String unitName, Label numLabel){
        if (!unitName.equals("spy")) {
            numLabel.setText(
                    String.valueOf(map.getTerritory(srcTerritoryName).getTroopNumUnits(unitName)));
        }else{
            numLabel.setText(
                    String.valueOf(map.getTerritory(srcTerritoryName).getSpyTroopNumUnits(playerName)));
        }
    }

    /** Sets the choice box with units that a player could upgrade TO. */
    private void setUpgradeToChoiceBox(String oldTalentName) {
        StyleMapping mapping = new StyleMapping();
        newTalent.getItems().removeAll(upgradeTo);
        upgradeTo.clear();
        ArrayList<String> talentNames = mapping.getTalentNames();
        int indexFrom = talentNames.indexOf(oldTalentName);
        if (oldTalentName.equals("Undergrad")) {
            upgradeTo.add("Spy");
        }
        for (String talentName : talentNames) {
            int indexTo = talentNames.indexOf(talentName);
            if (indexTo > indexFrom) {
                upgradeTo.add(talentName);
            }
        }
        newTalent.getItems().addAll(upgradeTo);
    }

    /** Returns a attack ActionInfo object based on fields entered by the user in the view. */
    @Override
    protected ActionInfo getActionInfo() throws IllegalArgumentException {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionRuleCheckerHelper checker = new ActionRuleCheckerHelper();
        String error = null;
        ActionInfo info = null;
        if (newUnitName.equals("spy")) {
            info =
                    af.createUpgradeSpyUnitActionInfo(
                            playerName,
                            srcTerritoryName,
                            parsePosIntFromTextField(numList.get(0).getText()));
            error = checker.checkRuleForUpgradeSpy(info, map);
        } else {
            info =
                    af.createUpgradeUnitActionInfo(
                            playerName,
                            srcTerritoryName,
                            oldUnitName,
                            newUnitName,
                            parsePosIntFromTextField(numList.get(0).getText()));
            error = checker.checkRuleForUpgradeUnit(info, map);
        }
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return info;
    }

    /** Triggered when player confirms their attack action by clicking on the Confirm button. */
    @FXML
    public void onAction(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            try {
                clearErrorMessage();
                ActionInfo info = getActionInfo();
                updateTotalCost(info, "tech");
                String success;
                if (!newUnitName.equals("spy")) {
                    success = model.getPlayer().tryIssueUpgradeUnitOrder(info);
                }
                else{
                    success = model.getPlayer().tryIssueUpgradeSpyUnitOrder(info);
                }
                if (success != null) {
                    setErrorMessage(success);
                } else {
                    Stage popup = (Stage) (((Node) ae.getSource()).getScene().getWindow());
                    popup.close();
                    loadNextPhase(mainPage);
                }
            } catch (IllegalArgumentException e) {
                setErrorMessage(e.getMessage());
            }
        } else {
            throw new IllegalArgumentException("Invalid ActionEvent source " + source);
        }
    }
}
