package edu.duke.ece651.risk.client;

import java.util.ArrayList;
import java.util.HashSet;

import edu.duke.ece651.risk.shared.*;

public class MapTextView {

    private ArrayList<String> playerNames;

    public MapTextView(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    public String displayMap(WorldMap toDisplay) {
        String ans = "";
        for (String name : playerNames) {
            ans = ans + onePlayerSection(name, toDisplay);
        }
        return ans;
    }

    private String onePlayerSection(String playerName, WorldMap toDisplay) {
        String ans = playerName + ":\n-------------\n";
        HashSet<Territory> myTerritories = toDisplay.getPlayerTerritories(playerName);
        for (Territory t : myTerritories) {
            ans = ans + oneTerritoryLine(t);
        }
        return ans;
    }

    private String oneTerritoryLine(Territory territory) {
        String ans = "";
        HashSet<Territory> neighbors = territory.getMyNeighbors();
        String unitNum = String.valueOf(territory.getNumUnits());

        // unit number part
        int digitLength = 5;
        for (int i = 0; i < digitLength - unitNum.length(); i++) {
            ans = ans + " ";
        }
        ans = ans + unitNum;
        ans = ans + " units in ";

        // this territory name part
        ans = ans + territory.getName();

        // neighbors part;
        ans = ans + " (next to: ";
        String sep = "";
        for (Territory t : neighbors) {
            ans = ans + sep;
            ans = ans + t.getName();
            sep = ", ";
        }
        ans = ans + ")\n";

        return ans;
    }

}
