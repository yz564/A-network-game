package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.*;

public class MapTextView {

    private WorldMap currentMap;

    public MapTextView() {
        this.currentMap = null;
    }

    public MapTextView(WorldMap currentMap) {
        this.currentMap = currentMap;
    }

    public String displayMap(WorldMap toDisplay) {
        return "the map";
    }

    private WorldMap getCurrentMap() {
        return currentMap;
    }

    private void replaceMap(WorldMap newMap) {
        this.currentMap = newMap;
    }

}
