package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.Territory;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SelectActionController extends Controller implements Initializable {
    WorldMap map;
    String playerName;
    String nextOnCompleteTurn;
    String nextOnLeave;
    String nextOnGameEnd;
    String selectedSrc;
    String selectedDest;

    @FXML ImageView mapImageView;
    @FXML Circle charSelected;
    @FXML Label nameSelected;
    @FXML ArrayList<ToggleButton> labelList;
    @FXML ArrayList<Label> numList;
    @FXML ArrayList<GridPane> actionList;
    @FXML GridPane actionPlayer;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public SelectActionController(App model) {
        super(model);
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
        this.playerName = model.getPlayer().getName();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set map image according to number of players
        helper.initializeMap(map, mapImageView);
        // set coloring for each territory label
        helper.initializeTerritoryLabelByOwner(map, labelList);
        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(
                model.getPlayer(), labelList, model.getPlayer().getName());
        // set image and label for selected character
        helper.initializeSelectedCharacter(model, charSelected, nameSelected);
        // set tooltip for player info
        helper.initializePlayerInfoTooltip(map, model.getPlayer().getName(), charSelected);
        // set total number of units for each territory
        helper.initializeTerritoryTotalNumUnitsLabels(model.getPlayer(), labelList, numList);
        // disable territory button for enemy territory
        helper.initializeTerritoryButtons(model, labelList);
        // set player action buttons
        setPlayerActionButtons(actionPlayer, "upgradeTech", "researchCloaking", "researchPatent");

        this.nextOnCompleteTurn = "selectAction";
        this.nextOnLeave = "joinRoom";
        this.nextOnGameEnd = "gameEnd";
    }

    public void onSelectTerritory(ActionEvent ae) {
        Object source = ae.getSource();
        if (source instanceof ToggleButton) {
            ToggleButton selectedTerritory = (ToggleButton) source;
            if (selectedTerritory.isSelected()) {
                setActionVisible(selectedTerritory);
                for (ToggleButton otherTerritory : labelList) {
                    if (!otherTerritory.getId().equals(selectedTerritory.getId())) {
                        otherTerritory.setSelected(false);
                    }
                }
            } else {
                for (GridPane actionPane : actionList) {
                    for (Node child : actionPane.getChildren()) {
                        child.setVisible(false);
                    }
                }
            }
        } else {
            throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid.");
        }
    }

    private void setActionVisible(ToggleButton territoryButton) {
        StyleMapping mapping = new StyleMapping();
        HashMap<String, Boolean> limited = model.getPlayer().getIsLimitedActionUsed();
        Territory selectedTerritory =
                map.getTerritory(mapping.getTerritoryName(territoryButton.getId()));
        this.selectedSrc = selectedTerritory.getName();
        String playerName = model.getPlayer().getName();
        for (String territoryName : map.getMyTerritories()) {
            int id = mapping.getLabelId(territoryName);
            Territory territory = map.getTerritory(territoryName);
            GridPane actionPane = actionList.get(id);
            ArrayList<String> actionList = new ArrayList<>();
            if (selectedTerritory.isBelongTo(playerName)) {
                if (selectedTerritory.getName().equals(territoryName)) {
                    if (selectedTerritory.getTotalNumUnits() > 0) {
                        actionList.add("upgradeTalents");
                    }
                    if (map.getPlayerInfo(playerName).getIsCloakingResearched()) {
                        actionList.add("cloaking");
                    }
                } else if (selectedTerritory.isReachableTo(territory)) {
                    if (selectedTerritory.getTotalNumUnits() > 0) {
                        actionList.add("move");
                    }
                    if (selectedTerritory.getSpyTroopNumUnits(playerName) > 0
                            && !limited.get("move spy")) {
                        actionList.add("moveSpy");
                    }
                } else if (selectedTerritory.isAdjacentTo(territory)) {
                    if (selectedTerritory.getTotalNumUnits() > 0) {
                        actionList.add("attack");
                    }
                    if (selectedTerritory.getSpyTroopNumUnits(playerName) > 0
                            && !limited.get("move spy")) {
                        actionList.add("moveSpy");
                    }
                } else {
                    for (Node child : actionPane.getChildren()) {
                        child.setVisible(false);
                    }
                }
            } else {
                if (selectedTerritory.isAdjacentTo(territory) && !limited.get("move spy")) {
                    actionList.add("moveSpy");
                }
            }
            setActionButtons(actionPane, actionList);
        }
    }

    @FXML
    private void setActionButtons(GridPane actionPane, ArrayList<String> actions) {
        ObservableList<Node> childrenList = actionPane.getChildren();
        for (Node child : childrenList) {
            int id = childrenList.indexOf(child);
            Button actionButton = (Button) child;
            if (id >= actions.size()) {
                actionButton.setVisible(false);
            } else {
                Image actionIcon =
                        new Image("ui/icons/" + actions.get(id) + ".png", 500, 500, false, true);
                ImageView actionImage = new ImageView(actionIcon);
                actionImage.setFitHeight(23);
                actionImage.setFitWidth(23);
                actionButton.setGraphic(actionImage);
                actionButton.getStyleClass().clear();
                actionButton.getStyleClass().addAll("action-button", "action-" + actions.get(id));
                actionButton.setOnAction(ae -> onSelectAction(ae, actions.get(id)));
                actionButton.setVisible(true);
            }
        }
    }

    @FXML
    public void setPlayerActionButtons(GridPane actionPane, String... actions) {
        HashMap<String, Boolean> limited = model.getPlayer().getIsLimitedActionUsed();
        ObservableList<Node> childrenList = actionPane.getChildren();
        for (Node child : childrenList) {
            int id = childrenList.indexOf(child);
            Button actionButton = (Button) child;
            if (actions[id].equals("researchCloaking")) {
                if (!map.getPlayerInfo(playerName).canCloakingResearched()
                        || map.getPlayerInfo(playerName).getIsCloakingResearched()) {
                    actionButton.setDisable(true);
                }
            }
            if (actions[id].equals("upgradeTech")) {
                if (limited.get("upgrade tech")) {
                    actionButton.setDisable(true);
                }
            }
            if (actions[id].equals("researchPatent")) {
                if (checkZeroTotalUnits()
                        || limited.get("research patent")
                        || !model.getPlayer().isResearchPatentAvailable()) {
                    actionButton.setDisable(true);
                }
            }
            actionButton.setOnAction(ae -> onSelectAction(ae, actions[id]));
        }
    }

    private boolean checkZeroTotalUnits() {
        int totalNum = 0;
        for (Label numLabel : numList) {
            totalNum += Integer.valueOf(numLabel.getText());
        }
        return totalNum == 0;
    }

    @FXML
    public void onSelectAction(ActionEvent ae, String action) {
        Object source = ae.getSource();
        try {
            if (source instanceof Button) {
                Button selectedAction = (Button) source;
                StyleMapping mapping = new StyleMapping();
                String destId = "label" + selectedAction.getParent().getId().substring(6);
                this.selectedDest = mapping.getTerritoryName(destId);
                this.next = action + "Action";
                loadActionPopup(
                        (Stage) (((Node) ae.getSource()).getScene().getWindow()),
                        selectedSrc,
                        selectedDest);
            } else {
                throw new IllegalArgumentException(
                        "Action event " + ae.getSource() + " is invalid.");
            }
        } catch (Exception e) {
            System.out.println(e);
            setErrorMessage(e.getMessage());
        }
    }

    /** Switches to the view that asks player join a room. */
    @FXML
    public void onLeaveGame(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) { // go to join room
            model.requestLeave();
            next = nextOnLeave;
            loadNextPhase((Stage) mapImageView.getScene().getWindow());
        } else {
            throw new IllegalArgumentException(
                    "Action event " + ae.getSource() + " is invalid for onAction().");
        }
    }

    /** Takes the user to a view that asks them to wait for other players to finish their turn. */
    /*
    @FXML
    public void onCompleteTurn(ActionEvent ae) throws Exception {
      Object source = ae.getSource();
      if (source instanceof Button) {
        String status = model.getPlayer().doneIssueOrders();
        if (status != null) {
          this.next = nextOnGameEnd;
        } else {
          this.next = nextOnCompleteTurn;
        }
        loadNextPhase((Stage) mapImageView.getScene().getWindow());
      } else {
        throw new IllegalArgumentException(
            "Action event " + ae.getSource() + " is invalid for onAction().");
      }
    }
    */

    @FXML
    public void onCompleteTurn(ActionEvent ae) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Button) {
            model.getPlayer().doneIssueOrders();
            this.next = "loading";
            loadNextPhase((Stage) mapImageView.getScene().getWindow());
        } else {
            throw new IllegalArgumentException(
                    "Action event " + ae.getSource() + " is invalid for onAction().");
        }
    }
}
