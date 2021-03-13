package edu.duke.ece651.risk.shared;

import java.util.HashSet;

/*
 * BasicTerritory is a simple territory that contains a
 * single troop of units inside it and has a name.
 */
public class BasicTerritory implements Territory {
  private Troop myTroop;
  private String territoryName;

  /* Construct a BasicTerritory object.
   * @param name is the name to assign to the territory.
   * @param toAdd is the Troop to add the the territory.
   */
  public BasicTerritory(String name, Troop toAdd) {
    this.myTroop = toAdd;
    this.territoryName = name;
  }

  /* Construct a BasicTerritory object.
   * @param name is the name to assign to the territory.
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
  
  /* Return the troop present inside the territory.
   */
  public Troop getTroop() {
    return myTroop;
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
    return "Territory " + this.territoryName +
      " contains the following troop:\n" + myTroop.toString();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}
