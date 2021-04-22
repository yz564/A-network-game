package edu.duke.ece651.risk.shared;

import java.util.HashMap;
import java.util.HashSet;

public abstract class AbstractTerritory implements Territory {
  private static final long serialVersionUID = -8815409601117401416L;
  protected final String territoryName;
  protected String ownerName;
  protected HashMap<String, Territory> myNeighbors;
  protected HashMap<String, Troop> myTroops;
  protected HashMap<String, Troop> spyTroop=new HashMap<String, Troop>();

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
  public HashMap<String, Territory> getReachableNeighbors() {
    HashMap<String, Territory> neighbors = new HashMap<String, Territory>();
    for (String neighborName : this.myNeighbors.keySet()) {
      if (myNeighbors.get(neighborName).isBelongTo(this.ownerName)) {
        neighbors.put(neighborName, myNeighbors.get(neighborName));
      }
    }
    return neighbors;
  }

  /**
   * Find all the reachable territories from the current territory, where
   * reachable is defined as connected by adjacent territories belonging to the
   * same owner as the current territory.
   *
   * @return a HashMap where key is String representing the territory name and
   *         value is the Territory object
   */
  protected HashMap<String, Territory> findReachableTerritories() {
    HashMap<String, Territory> reachable = new HashMap<>();
    int size = reachable.size();
    reachable.put(this.territoryName, this);
    while (size != reachable.size()) {
      size = reachable.size();
      HashSet<String> names = new HashSet<>(reachable.keySet());
      for (String name : names) {
        HashMap<String, Territory> neighbors = reachable.get(name).getReachableNeighbors();
        reachable.putAll(neighbors);
      }
    }
    return reachable;
  }

  @Override
  public boolean isReachableTo(Territory toReach) {
    if (findReachableTerritories().containsValue(toReach)) {
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

  @Override
  public int getTotalNumUnits(){
    int totalUnits = 0;
    for (String troopName : myTroops.keySet()) {
      totalUnits += myTroops.get(troopName).getNumUnits();
    }
    return totalUnits;
  }

  @Override
  public Troop getSpyTroop(String playerName){
    if (isExistSpyTroop(playerName)) {
      return spyTroop.get(playerName);
    }
    return null;
  }
  @Override
  public Boolean isExistSpyTroop(String playerName){
    if (spyTroop.containsKey(playerName)){ 
      return true;
    }
    return false;
  }
  /**
   *return true if added
   */
  @Override
  public Boolean tryAddSpyTroopUnits(String playerName, int addNum){
    if (!isExistSpyTroop(playerName)){
      Troop newSpy=new LevelTroop("spy",0,0,1,20);
      Boolean temp=newSpy.tryAddUnits(addNum);
      spyTroop.put(playerName,newSpy);
      return temp;
    }
    Troop oldSpy=spyTroop.get(playerName);
    return oldSpy.tryAddUnits(addNum);
  }
  /**
   *if the play's spy units are all removed, the playerName will be removed from spyTroop
   */
  @Override
  public Boolean tryRemoveSpyTroopUnits(String playerName, int removeNum){
    if (!isExistSpyTroop(playerName)){
      return false;
    }
    Troop oldSpy=spyTroop.get(playerName);
    Boolean temp=oldSpy.tryRemoveUnits(removeNum);
      if(temp && oldSpy.getNumUnits()==0){
        spyTroop.remove(playerName);
      }
    return temp;
  }

  public int getSpyTroopNumUnits(String playerName){
  if (!isExistSpyTroop(playerName)){
      return 0;
    }
  Troop oldSpy=spyTroop.get(playerName);
  return oldSpy.getNumUnits();
  }

}
