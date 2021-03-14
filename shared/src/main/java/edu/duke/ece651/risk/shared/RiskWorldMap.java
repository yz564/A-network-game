package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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
  public boolean tryAddTerritory(String toAdd) {
    if (myTerritories.keySet().contains(toAdd)) {
      return false;
    } else {
      myTerritories.put(toAdd, new BasicTerritory(toAdd, 0));
      return true;
    }
  }

  @Override
  public Territory getTerritory(String name) {
    return myTerritories.get(name);
  }

  @Override
  public boolean tryAddInitGroup(int group, String toAdd) {
    ArrayList<String> curr = initGroups.getOrDefault(group, new ArrayList<String>());
    if (curr.contains(toAdd)) {
      return false;
    }
    curr.add(toAdd);
    initGroups.put(group, curr);
    return true;
  }

  @Override
  public ArrayList<String> getInitGroup(int group) {
    return initGroups.get(group);
  }

  @Override
  public boolean tryAssignInitOwner(int group, String playerName) {
    if (initGroups.get(group) == null) {
      return false;
    }
    for (String territoryName : initGroups.get(group)) {
      myTerritories.get(territoryName).tryAssignOwner(playerName);
    }
    return true;
  }

  @Override
  public HashSet<Territory> getPlayerTerritories(String playerName) {
    HashSet<Territory> territories = new HashSet<Territory>();
    for (String territoryName : myTerritories.keySet()) {
      Territory t = getTerritory(territoryName);
      if (t.getOwnerName().equals(playerName)) {
        territories.add(t);
      }
    }
    return territories;
  }

  @Override
  public boolean tryChangeOwner(String territoryName, String playerName) {
    Territory territory = getTerritory(territoryName);
    if (territory != null) {
      return territory.tryAssignOwner(playerName);
    } else {
      return false;
    }
  }
}
