package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.*;

public class MapTextView {

    private ArrayList<String> playerNames;
    final private String myPlayerName;

    public MapTextView(ArrayList<String> playerNames, String myPlayerName) {
        this.playerNames = playerNames;
        this.myPlayerName = myPlayerName;
    }

    public String displayMap(WorldMap toDisplay) {
        return "the map";
    }

}
