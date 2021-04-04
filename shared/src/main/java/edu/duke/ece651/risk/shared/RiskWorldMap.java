package edu.duke.ece651.risk.shared;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * RiskWorldMap implements the WorldMap interface that contains the contains the
 * mapping of territories to its name, and the mapping of initial grouping to
 * list of territories names
 */
public abstract class RiskWorldMap implements WorldMap {

  private static final long serialVersionUID = -8601217585700186444L;
  protected HashMap<String, Territory> myTerritories;
  protected HashMap<Integer, ArrayList<String>> initGroups;
  protected HashMap<String, PlayerInfo> playersInfo;

  public RiskWorldMap() {
    this.myTerritories = new HashMap<String, Territory>();
    this.initGroups = new HashMap<Integer, ArrayList<String>>();
    this.playersInfo = new HashMap<String, PlayerInfo>();
  }

  /**
   * Creates the territories on the given world map
   * 
   * @param names     is the array of territories names
   * 
   * @param groups is the array of adjacency lists that corresponds to the
   *                  territory names
   */
  public RiskWorldMap(String[] names, int[] groups) {
    this();
    makeInitGroups(names, groups);
  }

  /**
   * Creates the initial grouping of territories on the given world map
   * 
   * @param names  is the array of territories names
   * 
   * @param groups is the array of initial grouping number that corresponds to the
   *               territory names
   */
  protected void makeInitGroups(String[] names, int[] groups) {
    for (int i = 0; i < names.length; i++) {
      this.tryAddInitGroup(groups[i], names[i]);
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
      myTerritories.get(territoryName).setOwnerName(playerName);
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
      territory.setOwnerName(playerName);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public boolean tryAddTerritory(Territory toAdd) {
    if (myTerritories.keySet().contains(toAdd.getName())) {
      return false;
    } else {
      myTerritories.put(toAdd.getName(), toAdd);
      return true;
    }
  }

  @Override
  public boolean tryAddPlayerInfo(PlayerInfo newPlayer) {
    if (playersInfo.keySet().contains(newPlayer.getPlayerName())) {
      return false;
    } else {
      playersInfo.put(newPlayer.getPlayerName(), newPlayer);
      return true;
    }
  }

  @Override
  public PlayerInfo getPlayerInfo(String playerName) {
    return playersInfo.get(playerName);
  }

  @Override
  public int inWhichInitGroup(String territoryName){
    for (int groupId : initGroups.keySet()) {
      if (initGroups.get(groupId).contains(territoryName)){
        return groupId;
      }
    }
    return 0;
  }
}


