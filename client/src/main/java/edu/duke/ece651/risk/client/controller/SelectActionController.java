package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SelectActionController extends Controller implements Initializable {
  WorldMap map;
  String nextOnCompleteTurn;
  String nextOnLeave;
  String nextOnMoveAction;
  String nextOnAttackAction;
  String nextOnUpgradeTalentsAction;
  String nextOnUpgradeTechAction;
  String nextOnMoveSpy;
  String nextOnUpgradeSpy;
  String nextOnGameEnd;

  @FXML ImageView mapImageView;
  //@FXML Label playerInfo;
  //@FXML SplitMenuButton actionMenu;
  @FXML ArrayList<Label> labelList;
  @FXML GridPane action;

  /**
   * Constructor that initializes the model.
   * @param model is the backend of the game.
   */
  public SelectActionController(App model) {
    super(model);
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
    helper.initializeTerritoryLabelByOwner(model.getPlayer().getMap(), labelList);
    // set tooltip for each territory label
    helper.initializeTerritoryTooltips(model.getPlayer().getMap(), labelList);
    // set tooltip for player info
    //helper.initializePlayerInfoTooltip(
        //model.getPlayer().getMap(), model.getPlayer().getName(), playerInfo);
    // set coloring for player info
    //helper.initializeTerritoryPlayerInfoColor(model, playerInfo);

    /*for (Node child : action.getChildren()){
      if (child instanceof Circle){
        Circle actionButton = (Circle) child;
        Image image = new Image("ui/icons/attack.png");
        actionButton.setFill(new ImagePattern(image));
        //actionButton.getStyleClass().add("action-"+String.valueOf(1));
      }
    }*/

    this.nextOnCompleteTurn = "selectAction";
    this.nextOnMoveAction = "moveAction";
    this.nextOnAttackAction = "attackAction";
    this.nextOnUpgradeTalentsAction = "upgradeTalentsAction";
    this.nextOnUpgradeTechAction = "upgradeTechAction";
    this.nextOnMoveSpy = "moveSpyAction";
    this.nextOnUpgradeSpy = "upgradeSpyAction";
    this.nextOnLeave = "joinRoom";
    this.nextOnGameEnd = "gameEnd";
  }

  /**
   * Opens a new view that asks user to move their talents within their territories.
   *//*
  public void onSelectMove(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof MenuItem) {
      next = nextOnMoveAction;
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    } else {
      throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid.");
    }
  }

  *//**
   * Opens a new view that asks user to attack enemy territory.
   *//*
  public void onSelectAttack(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof MenuItem) {
      next = nextOnAttackAction;
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    } else {
      throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid.");
    }
  }

  *//**
   * Opens a new view that asks user to upgrade their talents.
   *//*
  public void onSelectUpgradeTalents(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof MenuItem) {
      next = nextOnUpgradeTalentsAction;
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    } else {
      throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid.");
    }
  }

  *//**
   * Opens a new view that asks user to upgrade their technology level.
   *//*
  public void onSelectUpgradeTech(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof MenuItem) {
      next = nextOnUpgradeTechAction;
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    } else {
      throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid.");
    }
  }

  *//**
   * Opens a new view that asks user to move their spies.
   * @param ae is the action event that triggers this function.
   *//*
  public void onSelectMoveSpy(ActionEvent ae) throws IOException {
    Object source = ae.getSource();
    if (source instanceof MenuItem) {
      next = nextOnMoveSpy;
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    }
    else {
      throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid.");
    }
  }

  *//**
   * Opens a new view that asks user to upgrade their spies.
   * @param ae is the action event that triggers this function.
   *//*
  @FXML
  public void onSelectUpgradeSpy(ActionEvent ae) throws IOException {
    Object source = ae.getSource();
    if (source instanceof MenuItem) {
      next = nextOnUpgradeSpy;
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    }
    else {
      throw new IllegalArgumentException("Action event " + ae.getSource() + " is invalid.");
    }
  }

  @FXML
  public void onSelectResearchCloaking(ActionEvent ae) {}

  @FXML
  public void onSelectCloaking(ActionEvent ae) {}

  *//**
   * Switches to the view that asks player join a  room.
   *//*
  @FXML
  public void onLeavingGame(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) { // go to join room
      model.requestLeave();
      next = nextOnLeave;
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    } else {
      throw new IllegalArgumentException(
          "Action event " + ae.getSource() + " is invalid for onAction().");
    }
  }

  *//**
   * Takes the user to a view that asks them to wait for other players to finish their turn.
   *//*
  @FXML
  public void onCompletingTurn(ActionEvent ae) throws Exception {
    Object source = ae.getSource();
    if (source instanceof Button) {
      String status = model.getPlayer().doneIssueOrders();
      if (status != null) {
        this.next = nextOnGameEnd;
      } else {
        this.next = nextOnCompleteTurn;
      }
      loadNextPhase((Stage) actionMenu.getScene().getWindow());
    } else {
      throw new IllegalArgumentException(
          "Action event " + ae.getSource() + " is invalid for onAction().");
    }
  }*/
}
