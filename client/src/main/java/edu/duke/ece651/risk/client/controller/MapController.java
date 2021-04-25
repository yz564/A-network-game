package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.Player;
import edu.duke.ece651.risk.client.TerritoryInfo;
import edu.duke.ece651.risk.client.view.PhaseChanger;
import edu.duke.ece651.risk.client.view.StyleMapping;
import edu.duke.ece651.risk.shared.PlayerInfo;
import edu.duke.ece651.risk.shared.WorldMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class MapController extends Controller {
    Player player;
    WorldMap map;
    String playerName;
    PlayerInfo playerInfo;
    int numPlayers;
    int numTerritory;

    HashMap<String, Node> tooltipInfo;
    HashMap<String, Node> tooltipTalents;
    @FXML ArrayList<ToggleButton> labelList;
    @FXML ArrayList<Tooltip> tooltipList;

    public MapController(App model) {
        super(model);
        // set player and map from model
        this.player = model.getPlayer();
        this.map = player.getMap();
        this.playerName = player.getName();
        this.playerInfo = map.getPlayerInfo(playerName);
        // set number of players for the game
        this.numPlayers = map.getNumPlayers();
        // set number of territories for the game
        this.numTerritory = map.getMyTerritories().size();
        this.tooltipInfo = new HashMap<>();
        this.tooltipTalents = new HashMap<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    private void extractTooltipInfoFromGridPane(GridPane gridPane) {
        int[][] gridIndices = {
            {1, 2}, {2, 3}, {2, 6}, {2, 9}, {2, 11}, {3, 11}, {5, 2}, {6, 2}, {5, 3}, {6, 3},
            {5, 9}, {5, 12}
        };
        String[] tooltipInfoName = {
            "name",
            "size",
            "food",
            "tech",
            "domainIcon",
            "domain",
            "ownerDomainIcon",
            "ownerDomain",
            "ownerName",
            "owner",
            "spy",
            "cloak"
        };

        int[][] gridIndicesTalents = {{8, 3}, {8, 6}, {8, 9}, {8, 12}, {10, 3}, {10, 6}, {10, 9}};
        String[] tooltipTalentsName = {
            "level0", "level1", "level2", "level3", "level4", "level5", "level6"
        };
        for (Node child : gridPane.getChildren()) {
            for (int i = 0; i < gridIndices.length; i++) {
                if (GridPane.getRowIndex(child) == gridIndices[i][0]
                        && GridPane.getColumnIndex(child) == gridIndices[i][1]) {
                    tooltipInfo.put(tooltipInfoName[i], child);
                }
            }
            for (int i = 0; i < gridIndicesTalents.length; i++) {
                if (GridPane.getRowIndex(child) == gridIndicesTalents[i][0]
                        && GridPane.getColumnIndex(child) == gridIndicesTalents[i][1]) {
                    tooltipTalents.put(tooltipTalentsName[i], child);
                }
            }
        }
    }

    public void initializeTerritoryTooltip() {
        StyleMapping mapping = new StyleMapping();
        for (int i = 0; i < numTerritory; i++) {
            String territoryLabel = labelList.get(i).getId();
            String territoryName = mapping.getTerritoryName(territoryLabel);
            TerritoryInfo info = player.getTerritoryInfo(territoryName);
            boolean vizStatus = playerInfo.getOneVizStatus(territoryName);
            GridPane gridPane = (GridPane) tooltipList.get(i).getGraphic();
            extractTooltipInfoFromGridPane(gridPane);
            // set basic info
            initializeBasicInfo(info, territoryName);
            // set own info
            initializeOwnInfo(info);
            // set visible info
            initializeVisibleInfo(info, vizStatus);
            // set talent info
            initializeTalentsInfo(info, vizStatus, gridPane);
        }
    }

    public void initializeBasicInfo(TerritoryInfo info, String territoryName) {
        ((Label) tooltipInfo.get("name")).setText("< " + territoryName + " >");
        ((Label) tooltipInfo.get("size")).setText(String.valueOf(info.getSize()));
        ((Label) tooltipInfo.get("food")).setText(String.valueOf(info.getFoodProduction()));
        ((Label) tooltipInfo.get("tech")).setText(String.valueOf(info.getFoodProduction()));
        ((Label) tooltipInfo.get("domain")).setText(String.valueOf(info.getDomain()));
        ((ImageView) tooltipInfo.get("domainIcon"))
                .setImage(new Image("ui/icons/tooltip/" + info.getDomain().toLowerCase() + ".png"));
    }

    public void initializeOwnInfo(TerritoryInfo info) {
        ((Label) tooltipInfo.get("spy")).setText(String.valueOf(info.getPlayerSpyNum()));
        if (info.getOwnerName().equals(playerName)) {
            ((Label) tooltipInfo.get("cloak")).setText(String.valueOf(info.getCloakingTurns()));
        } else {
            ((Label) tooltipInfo.get("cloak")).setText("-");
        }
    }

    public void initializeVisibleInfo(TerritoryInfo info, boolean vizStatus) {
        String ownerDomain = playerInfo.getResearchDomain();
        ((Label) tooltipInfo.get("ownerName")).setText(info.getOwnerName());
        if (!info.getOwnerName().equals("Unknown")) {
            ((Label) tooltipInfo.get("ownerDomain")).setText(ownerDomain);
            ((ImageView) tooltipInfo.get("ownerDomainIcon"))
                    .setImage(new Image("ui/icons/tooltip/" + ownerDomain.toLowerCase() + ".png"));
        } else {
            ((Label) tooltipInfo.get("ownerDomain")).setText("Unknown");
            ((ImageView) tooltipInfo.get("ownerDomainIcon"))
                    .setImage(new Image("ui/icons/tooltip/unknown.png"));
        }
        if (!vizStatus) {
            tooltipInfo.get("owner").setStyle("-fx-text-fill: #988675");
            tooltipInfo.get("ownerName").setStyle("-fx-text-fill: #988675");
            tooltipInfo.get("ownerDomain").setStyle("-fx-text-fill: #988675");
            ((ImageView) tooltipInfo.get("ownerDomainIcon"))
                    .setImage(
                            blendColor(
                                    ((ImageView) tooltipInfo.get("ownerDomainIcon")).getImage(),
                                    Color.rgb(152, 134, 117)));
        }
    }

    public void initializeTalentsInfo(TerritoryInfo info, boolean vizStatus, GridPane gridPane) {
        for (int i = 0; i < tooltipTalents.size(); i++) {
            ((Label) tooltipTalents.get("level" + i))
                    .setText(String.valueOf(info.getOneTroopNum("level" + i)));
            if (!vizStatus) {
                tooltipTalents.get("level" + i).setStyle("-fx-text-fill: #988675");
                for (Node child : gridPane.getChildren()) {
                    if ((GridPane.getRowIndex(child)
                                    == GridPane.getRowIndex(tooltipTalents.get("level" + i)))
                            && (GridPane.getColumnIndex(child)
                                    == (GridPane.getColumnIndex(tooltipTalents.get("level" + i))
                                            - 1))) {
                        ((ImageView) child)
                                .setImage(
                                        blendColor(
                                                ((ImageView) child).getImage(),
                                                Color.rgb(152, 134, 117)));
                    }
                    if (GridPane.getRowIndex(child)
                                    == GridPane.getRowIndex(tooltipTalents.get("level" + i)) + 1
                            && GridPane.getColumnIndex(child)
                                    == (GridPane.getColumnIndex(tooltipTalents.get("level" + i))
                                            - 1)) {
                        child.setStyle("-fx-text-fill: #988675");
                    }
                }
            }
        }
    }

    public static Image blendColor(final Image sourceImage, final Color blendColor) {
        final double r = blendColor.getRed();
        final double g = blendColor.getGreen();
        final double b = blendColor.getBlue();
        final int w = (int) sourceImage.getWidth();
        final int h = (int) sourceImage.getHeight();
        final WritableImage outputImage = new WritableImage(w, h);
        final PixelWriter writer = outputImage.getPixelWriter();
        final PixelReader reader = sourceImage.getPixelReader();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                // Keeping the opacity of every pixel as it is.
                writer.setColor(x, y, new Color(r, g, b, reader.getColor(x, y).getOpacity()));
            }
        }
        return outputImage;
    }
}
