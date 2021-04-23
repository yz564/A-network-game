package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ResearchPatentActionController extends ActionController {
    ArrayList<String> selectedList;
    @FXML ArrayList<Label> srcNumList;
    @FXML ArrayList<ToggleButton> srcNameList;
    @FXML ArrayList<ImageView> srcImageList;
    @FXML ArrayList<GridPane> territoryList;
    @FXML Label oldPatentProgress;
    @FXML Label newPatentProgress;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public ResearchPatentActionController(App model, Stage mainPage) {
        super(model, null, null, mainPage);
        selectedList = new ArrayList<>();
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
        oldPatentProgress.setText(String.valueOf(map.getPlayerInfo(playerName).getPatentProgress()));
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set the info for each player territory
        helper.initializePlayerTerritoryInfo(map, playerName, srcNameList, srcNumList, srcImageList);
        // delete the rows for player territory with 0 units
        helper.initializeTerritoryRows(srcNumList, territoryList, grid);
        addTerritoryToggleChangeListeners("tech");
        try {
            updateTotalCost(getActionInfo(), "tech");
            updatePatentProgress(getActionInfo());
        } catch (Exception e) {
            setErrorMessage(e.getMessage());
        }
    }

    private void updateSelectedList(String territoryName, boolean selected) {
        if (selected){
            selectedList.add(territoryName);
        }
        else{
            selectedList.remove(territoryName);
        }
    }

    private void updatePatentProgress(ActionInfo actionInfo) throws IllegalArgumentException{
        ActionExecuter executer = new ActionExecuter();
        int newPatentProgressInt = Integer.parseInt(oldPatentProgress.getText()) + executer.calculatePatentProgress(map, actionInfo);
        newPatentProgress.setText(String.valueOf(newPatentProgressInt));
    }

    private void addTerritoryToggleChangeListeners(String resource) {
        for (ToggleButton nameToggle : srcNameList) {
            nameToggle.selectedProperty()
                    .addListener(
                            (observable, oldValue, newValue) -> {
                                try {
                                    clearErrorMessage();
                                    updateSelectedList(nameToggle.getText(), newValue.booleanValue());
                                    updateTotalCost(getActionInfo(), resource);
                                    updatePatentProgress(getActionInfo());
                                } catch (IllegalArgumentException e) {
                                    setErrorMessage(e.getMessage());
                                }
                            });
        }
    }

    /** Returns a attack ActionInfo object based on fields entered by the user in the view. */
    @Override
    protected ActionInfo getActionInfo() throws IllegalArgumentException {
        ActionInfoFactory af = new ActionInfoFactory();
        ActionRuleCheckerHelper checker = new ActionRuleCheckerHelper();
        ActionInfo info = af.createResearchPatentActionInfo(playerName, selectedList);
        String error = checker.checkRuleForResearchPatent(info, map);
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
                updateTotalCost(info, "tech");
                String success = model.getPlayer().tryIssueResearchPatentOrder(info);
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
