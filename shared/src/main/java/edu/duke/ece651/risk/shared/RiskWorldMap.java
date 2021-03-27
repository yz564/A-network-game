package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * RiskWorldMap implements the WorldMap interface that contains the contains the
 * mapping of territories to its name, and the mapping of initial grouping to
 * list of territories names
 */
public class RiskWorldMap implements WorldMap {

  private static final long serialVersionUID = -8601217585700186444L;
  private HashMap<String, Territory> myTerritories;
  private HashMap<Integer, ArrayList<String>> initGroups;
  // private HashMap<String, ArrayList<Territory>> territoryAdjacentLists;
  // private HashMap<String, String> myPlayers;

  /**
   * Creates the territories on the given world map
   * 
   * @param names     is the array of territories names
   * 
   * @param adjacency is the array of adjacency lists that corresponds to the
   *                  territory names
   */
  private void makeTerritories(String[] names, int[][] adjacency) {
    for (int i = 0; i < names.length; i++) {
      this.tryAddTerritory(names[i]);
    }
    for (int i = 0; i < names.length; i++) {
      for (int j = 0; j < adjacency[i].length; j++) {
        Territory t = this.getTerritory(names[i]);
        Territory n = this.getTerritory(names[adjacency[i][j]]);
        t.tryAddNeighbor(n);
      }
    }
  }

  /**
   * Creates the initial grouping of territories on the given world map
   * 
   * @param names  is the array of territories names
   * 
   * @param groups is the array of initial grouping number that corresponds to the
   *               territory names
   */
  private void makeInitGroups(String[] names, int[] groups) {
    for (int i = 0; i < names.length; i++) {
      this.tryAddInitGroup(groups[i], names[i]);
    }
  }

  /**
   * Construct a RiskWorldMap object. Initializes both the territories mapping and
   * initial grouping to empty hashmap
   */
  public RiskWorldMap() {
    this.myTerritories = new HashMap<String, Territory>();
    this.initGroups = new HashMap<Integer, ArrayList<String>>();
  }

  /**
   * Construct a RiskWorldMap object.
   * 
   * @param names     is an array of strings of territory names
   * @param adjacency is an array of arrays of indices of adjacent territories to
   *                  each territory
   * @param groups    is an array of integers which is the initial groups id of
   *                  each territory
   */
  public RiskWorldMap(String[] names, int[][] adjacency, int[] groups) {
    this.myTerritories = new HashMap<String, Territory>();
    this.initGroups = new HashMap<Integer, ArrayList<String>>();
    makeTerritories(names, adjacency);
    makeInitGroups(names, groups);
  }

  @Override
  public boolean tryAddTerritory(String toAdd) {
    if (myTerritories.keySet().contains(toAdd)) {
      return false;
    } else {
      myTerritories.put(toAdd, new BasicTerritory(toAdd, 0, 0, 0));
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
      myTerritories.get(territoryName).putOwnerName(playerName);
    }
    return true;
  }

  @Override
  public HashMap<String, Territory> getPlayerTerritories(String playerName) {
    HashMap<String, Territory> territories = new HashMap<String, Territory>();
    for (String territoryName : myTerritories.keySet()) {
      Territory t = getTerritory(territoryName);
      if (t.getOwnerName().equals(playerName)) {
        territories.put(territoryName, t);
      }
    }
    return territories;
  }

  @Override
  public boolean tryChangeOwner(String territoryName, String playerName) {
    Territory territory = getTerritory(territoryName);
    if (territory != null) {
      territory.putOwnerName(playerName);
      return true;
    } else {
      return false;
    }
  }
}
