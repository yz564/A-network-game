package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashMap;

/*
 * RiskWorldMap implements the WorldMap interface
 * that contains the contains the mapping of territories
 * to its name, and the mapping of initial grouping to list of territories names
 */
public class RiskWorldMap implements WorldMap {
  private HashMap<String, Territory> myTerritories;
  private HashMap<Integer, ArrayList<String>> initGroups;
  // private HashMap<String, ArrayList<Territory>> territoryAdjacentLists;
  // private HashMap<String, String> myPlayers;

  /*
   * Construct a RiskWorldMap object. Initializes both the territories mapping and
   * initial grouping to empty hashmap
   */
  public RiskWorldMap() {
    this.myTerritories = new HashMap<String, Territory>();
    this.initGroups = new HashMap<Integer, ArrayList<String>>();
  }

  @Override
  public void tryAddTerritory(String toAdd) {
    this.myTerritories.put(toAdd, new BasicTerritory(toAdd, 0));
  }

  @Override
  public Territory getTerritory(String name) {
    return myTerritories.get(name);
  }

  @Override
  public void tryAddInitGroup(int group, String toAdd) {
    ArrayList<String> curr = initGroups.getOrDefault(group, new ArrayList<String>());
    curr.add(toAdd);
    initGroups.put(group, curr);
  }

  @Override
  public ArrayList<String> getInitGroup(int group) {
    return initGroups.get(group);
  }

  @Override
  public void tryAssignInitOwner(int group, String playerName) {
    for (String territoryName : initGroups.get(group)) {
      myTerritories.get(territoryName).tryAssignOwner(playerName);
    }
  }
}
