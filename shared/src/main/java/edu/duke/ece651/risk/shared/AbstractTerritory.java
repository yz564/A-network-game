package edu.duke.ece651.risk.shared;

import java.util.HashMap;
import java.util.HashSet;

public abstract class AbstractTerritory implements Territory {
  private static final long serialVersionUID = -8815409601117401416L;
  protected final String territoryName;
  protected String ownerName;
  protected HashMap<String, Territory> myNeighbors;
  protected HashMap<String, Troop> myTroops;

  /**
   * Construct a AbstractTerritory object with default ownerName being null and
   * default myNeighbors by an empty HashMap.
   *
   * @param name   is the name to assign to the territory.
   * @param troops are the troops that are present in this territory. HashMap key
   *               is troop name, and value is the Troop objects representing
   *               troops in this territory.
   */
  public AbstractTerritory(String name, HashMap<String, Troop> troops) {
    this.territoryName = name;
    this.ownerName = null;
    this.myNeighbors = new HashMap<>();
    this.myTroops = troops;
  }

  @Override
  public String getName() {
    return territoryName;
  }

  @Override
  public String getOwnerName() {
    return ownerName;
  }

  @Override
  public void setOwnerName(String playerName) {
    this.ownerName = playerName;
  }

  @Override
  public boolean isBelongTo(String playerName) {
    if (ownerName != null) {
      return this.ownerName.equals(playerName);
    } else {
      return false;
    }
  }

  @Override
  public HashMap<String, Territory> getMyNeighbors() {
    return myNeighbors;
  }

  @Override
  public boolean tryAddNeighbor(Territory neighbor) {
    if (this.myNeighbors.put(neighbor.getName(), neighbor) != null) {
      return false;
    } else {
      return true;
    }
  }

  @Override
  public boolean isAdjacentTo(Territory neighbor) {
    return this.myNeighbors.containsKey(neighbor.getName());
  }

  @Override
  public boolean isReachableTo(Territory toReach) {
    HashMap<String, Territory> reachable = new HashMap<>();
    int size = reachable.size();
    reachable.put(this.territoryName, this);
    while (size != reachable.size()) {
      size = reachable.size();
      HashSet<String> names = new HashSet<>(reachable.keySet());
      for (String name : names) {
        HashMap<String, Territory> neighbors = reachable.get(name).getMyNeighbors();
        for (String neighborName : neighbors.keySet()) {
          if (neighbors.get(neighborName).isBelongTo(this.ownerName)) {
            reachable.put(neighborName, neighbors.get(neighborName));
          }
        }
      }
    }
    if (reachable.containsValue(toReach)) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public HashMap<String, Troop> getMyTroops() {
    return myTroops;
  }

  @Override
  public Troop getTroop(String troopName) {
    return myTroops.get(troopName);
  }

  @Override
  public int getTroopNumUnits(String troopName) {
    return myTroops.get(troopName).getNumUnits();
  }

  @Override
  public boolean isExistTroop(String troopName) {
    return myTroops.containsKey(troopName);
  }

  @Override
  public boolean tryAddTroopUnits(String troopName, int addNum) {
    if (isExistTroop(troopName)) {
      return myTroops.get(troopName).tryAddUnits(addNum);
    }
    return false;
  }

  @Override
  public boolean tryRemoveTroopUnits(String troopName, int removeNum) {
    if (isExistTroop(troopName)) {
      return myTroops.get(troopName).tryRemoveUnits(removeNum);
    }
    return false;
  }

  @Override
  public boolean trySetTroopUnits(String troopName, int setNum) {
    if (isExistTroop(troopName)) {
      return myTroops.get(troopName).trySetNumUnits(setNum);
    }
    return false;
  }

  @Override
  public boolean trySetNumUnits(HashMap<String, Integer> toSet) {
    for (String troopName : toSet.keySet()) {
      int setNum = toSet.get(troopName);
      // Checks if troop exists
      if (!isExistTroop(troopName)) {
        return false;
      }
      // Checks if all numUnits are valid for each troop
      if (!myTroops.get(troopName).isValidUnits(setNum)) {
        return false;
      }
    }
    for (String troopName : toSet.keySet()) {
      trySetTroopUnits(troopName, toSet.get(troopName));
    }
    return true;
  }

  @Override
  public HashMap<String, Integer> getAllNumUnits() {
    HashMap<String, Integer> allNumUnits = new HashMap<String, Integer>();
    for (String troopName : myTroops.keySet()) {
      allNumUnits.put(troopName, myTroops.get(troopName).getNumUnits());
    }
    return allNumUnits;
  }
}
