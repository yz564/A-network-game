package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/* Class responsible for registering territory group for a player.
 */
public class SelectTerritoryGroupController extends Controller
        implements Initializable, ErrorHandlingController {
    WorldMap map;

    @FXML ImageView mapImageView;
    @FXML Label errorMessage;
    @FXML ArrayList<ToggleButton> labelList;
    @FXML ArrayList<Circle> charList;
    @FXML ArrayList<Label> nameList;

    /**
     * Constructor that initializes the model.
     *
     * @param model is the backend of the game.
     */
    public SelectTerritoryGroupController(App model) {
        super(model);
        this.next = "loading";
    }

    /**
     * Sets various elements in the view to default values.
     *
     * @param location is the location of the FXML resource.
     * @param resources used to initialize the root object of the view.
     */
    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        // get map from client App
        this.map = model.getPlayer().getMap();
        InitializeControllerHelper helper = new InitializeControllerHelper();
        // set map image according to number of players
        helper.initializeMap(map, mapImageView);
        // set coloring for each territory label
        helper.initializeTerritoryLabelByGroup(map, labelList);
        // set tooltip for each territory label
        helper.initializeTerritoryTooltips(map, labelList);
        // set image and label for each character
        helper.initializeCharacter(map, charList, nameList);
    }

    /** Registers group one with the player. */
    @FXML
    public void onSelectingGroup(MouseEvent ae) throws Exception {
        int groupId = Integer.parseInt(((Node) ae.getSource()).getId().substring(4));
        assignTerritoryToPlayer(ae, groupId);
    }

    /**
     * Assigns a territory to a player. Or shows an error message on the screen if the group is
     * already assigned to a different player.
     *
     * @param ae is the action event that triggers this function.
     */
    private void assignTerritoryToPlayer(MouseEvent ae, int territoryGroup) throws Exception {
        Object source = ae.getSource();
        if (source instanceof Node) {
            clearErrorMessage();
            Boolean success = model.getPlayer().tryInitialization(String.valueOf(territoryGroup));
            if (!success) {
                errorMessage.setText(
                        "Character has already been taken by another player. Try choosing a different character.");
            } else {
                loadNextPhase((Stage) (((Node) ae.getSource()).getScene().getWindow()));
            }
        } else {
            throw new IllegalArgumentException("Invalid source " + source + " for ActionEvent");
        }
    }

    @Override
    public void setErrorMessage(String error) {
        errorMessage.setText(error);
    }

    @Override
    public void clearErrorMessage() {
        setErrorMessage(null);
    }
}
