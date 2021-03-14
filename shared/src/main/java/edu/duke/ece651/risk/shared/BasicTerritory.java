package edu.duke.ece651.risk.shared;

import java.util.HashSet;

/*
 * BasicTerritory is a simple territory that contains a
 * single troop of units inside it and has a name.
 * 
 * It also contains a hashset of neighboring territories 
 * that it is adjacent to and its owner's name.
 */
public class BasicTerritory implements Territory {
  private Troop myTroop;
  private String territoryName;
  private HashSet<Territory> myNeighbors;
  private String myOwnerName;

  /*
   * Construct a BasicTerritory object.
   * 
   * @param name is the name to assign to the territory.
   * 
   * @param toAdd is the Troop to add the the territory.
   */
  public BasicTerritory(String name, Troop toAdd) {
    this.myTroop = toAdd;
    this.territoryName = name;
    this.myNeighbors = new HashSet<Territory>();
    this.myOwnerName = null;
  }

  /*
   * Construct a BasicTerritory object.
   * 
   * @param name is the name to assign to the territory.
   * 
   * @param numUnits is the number of units to add to the territory.
   */
  public BasicTerritory(String name, int numUnits) {
    this(name, new BasicTroop(numUnits));
  }

  @Override
  public boolean tryAddUnits(int toAdd) {
    return myTroop.tryAddUnits(toAdd);
  }

  @Override
  public boolean tryRemoveUnits(int toRemove) {
    return myTroop.tryRemoveUnits(toRemove);
  }

  @Override
  public int getNumUnits() {
    return myTroop.getNumUnits();
  }

  @Override
  public String getName() {
    return territoryName;
  }

  /*
   * Return the troop present inside the territory.
   */
  public Troop getTroop() {
    return myTroop;
  }

  @Override
  public boolean trySetNumUnits(int numUnits) {
    return myTroop.trySetNumUnits(numUnits);
  }

  @Override
  public boolean equals(Object o) {
    if (o != null && o.getClass().equals(getClass())) {
      BasicTerritory other = (BasicTerritory) o;
      return this.myTroop.equals(other.getTroop()) && this.territoryName.equals(other.getName());
    }
    return false;
  }

  @Override
  public String toString() {
    return "Territory " + this.territoryName + " contains the following troop:\n" + myTroop.toString();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public boolean isAdjacentTo(Territory neighbor) {
    return this.myNeighbors.contains(neighbor);
  }

  @Override
  public boolean tryAddNeighbor(Territory neighbor) {
    return this.myNeighbors.add(neighbor);
  }

  @Override
  public String getOwnerName() {
    return myOwnerName;
  }

  @Override
  public boolean isBelongTo(String playerName) {
    if (myOwnerName != null) {
      return this.myOwnerName.equals(playerName);
    } else {
      return false;
    }
  }

  @Override
  public boolean tryAssignOwner(String playerName) {
    if (playerName != null) {
      this.myOwnerName = playerName;
      return true;
    } else {
      return false;
    }
  }
}
