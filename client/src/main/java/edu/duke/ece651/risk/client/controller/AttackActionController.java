package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class AttackActionController extends ActionController {
    @FXML ArrayList<Label> srcNumList;
    @FXML ArrayList<Label> destNumList;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public AttackActionController(App model, String srcName, String destName, Stage mainPage) {
        super(model, srcName, destName, mainPage);
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
        loadResourceInfo("food");
        addNumUnitsChangeListeners("food");
        addSliderChangeListener();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set the number of units for each talent types
        helper.initializeNumUnit(map, srcTerritoryName, destTerritoryName, srcNumList, destNumList);
        // set the max value for sliders for each talent types
        helper.initializeSliders(sliderList, srcNumList);
        // delete the rows for talent types with 0 units
        helper.initializeTalentRows(srcNumList, destNumList, grid);
        try {
            updateTotalCost(getActionInfo(), "food");
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    /** Returns a attack ActionInfo object based on fields entered by the user in the view. */
    @Override
    protected ActionInfo getActionInfo() throws IllegalArgumentException {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionRuleCheckerHelper checker = new ActionRuleCheckerHelper();
        HashMap<String, Integer> numUnits = getNumUnits();
        ActionInfo info = af.createAttackActionInfo(
                playerName, srcTerritoryName, destTerritoryName, numUnits);
        String error = checker.checkRuleForAttack(info, map);
        if (error != null){
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
                updateTotalCost(info, "food");
                String success = model.getPlayer().tryIssueAttackOrder(info);
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
