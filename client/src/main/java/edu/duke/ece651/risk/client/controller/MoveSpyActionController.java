package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.ActionInfo;
import edu.duke.ece651.risk.shared.ActionInfoFactory;
import edu.duke.ece651.risk.shared.ActionRuleCheckerHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MoveSpyActionController extends ActionController {
    @FXML Label srcNum;
    @FXML Label destNum;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public MoveSpyActionController(App model, String srcName, String destName, Stage mainPage) {
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
        srcNum.setText(
                String.valueOf(map.getTerritory(srcTerritoryName).getSpyTroopNumUnits(playerName)));
        destNum.setText(
                String.valueOf(map.getTerritory(srcTerritoryName).getSpyTroopNumUnits(playerName)));
        // set the max value for sliders for each talent types
        helper.initializeSliders(
                sliderList,
                new ArrayList<Label>() {
                    {
                        add(srcNum);
                    }
                });
        try {
            updateTotalCost(getActionInfo(), "food");
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    /** Returns a move ActionInfo object based on fields entered by the user in the view. */
    @Override
    protected ActionInfo getActionInfo() throws IllegalArgumentException {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionRuleCheckerHelper checker = new ActionRuleCheckerHelper();
        ActionInfo info =
                af.createMoveSpyActionInfo(
                        playerName,
                        srcTerritoryName,
                        destTerritoryName,
                        parsePosIntFromTextField(numList.get(0).getText()));
        String error = checker.checkRuleForMoveSpy(info, map);
        if (error != null) {
            throw new IllegalArgumentException(error);
        }
        return info;
    }

    /** Triggered when player confirms their move action by clicking on the Confirm button. */
    @FXML
    public void onAction(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            try {
                clearErrorMessage();
                ActionInfo info = getActionInfo();
                updateTotalCost(info, "food");
                String success = model.getPlayer().tryIssueMoveSpyOrder(info);
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
