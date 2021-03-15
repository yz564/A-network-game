package edu.duke.ece651.risk.client;

import java.util.ArrayList;
import java.util.HashSet;

import edu.duke.ece651.risk.shared.*;

public class MapTextView {

    /**
     * The list of all players' names.
     */
    private ArrayList<String> playerNames;

    /**
     * Constructs a MapTextView object with a given list of all players' name.
     * 
     * @param playerNames is the list of all players' names
     */
    public MapTextView(ArrayList<String> playerNames) {
        this.playerNames = playerNames;
    }

    /**
     * Display the game's world map with text with a given WorldMap object.
     * 
     * @param toDisplay a WorldMap represents the game world map to display.
     * @return a String that can be printed to display the world map.
     */
    public String displayMap(WorldMap toDisplay) {
        String ans = "";
        for (String name : playerNames) {
            ans = ans + onePlayerSection(name, toDisplay);
        }
        return ans;
    }

    /**
     * Display the game's world map with text for one player's section with a given
     * name and WorldMap object.
     * 
     * @param playerName Stirng the player's name. The player's section on the map
     *                   will be displayed.
     * @param toDisplay  a WorldMap represents the game world map to display.
     * @return a String that can be printed to display the world map section.
     */
    private String onePlayerSection(String playerName, WorldMap toDisplay) {
        String ans = playerName + ":\n-------------\n";
        HashSet<Territory> myTerritories = toDisplay.getPlayerTerritories(playerName);
        for (Territory t : myTerritories) {
            ans = ans + oneTerritoryLine(t);
        }
        return ans;
    }

    /**
     * Display one Territpry of the game's world map with text.
     * 
     * @param territory is a Territory object represents the territory to display.
     * @return a String that can be printed to display the world map territory.
     */
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
