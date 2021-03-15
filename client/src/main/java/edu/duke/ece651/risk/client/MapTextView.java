package edu.duke.ece651.risk.client;

import java.util.ArrayList;

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

    private String onePlayerSection(String playerName) {
        String ans = playerName + ":\n-------------";

        return ans;
    }

    private String oneTerritoryLine(Territory territory) {
        // ArrayList neighbors = territory.ge
        return "";
    }

}
