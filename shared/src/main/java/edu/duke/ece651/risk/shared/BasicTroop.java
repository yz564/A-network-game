package edu.duke.ece651.risk.shared;

/*
 * A troop contains an arbitrary number of a particular kind of units. E.g. a
 * troop could contain 10 soldiers or 5 knights but not both.
 */
public class BasicTroop implements Troop {
  private final String troopName;
  private final int unitLimit;
  private int numUnits;

  /*
   * Construct a BasicTroop object.
   * 
   * @param name is the name assigned to the troop. If name is null then it is set
   * to "Troop".
   * 
   * @param numunits is the number of units put in the troop.
   * 
   * @param unitLimit is the maximum amount of units that a troop can contain.
   */
  public BasicTroop(String name, int numUnits, int unitLimit) {
    if (name == null) {
      this.troopName = "Troop";
    } else {
      this.troopName = name;
    }
    if (numUnits < 0) {
      throw new IllegalArgumentException("A troop cannot have negative number of units.");
    }
    this.unitLimit = unitLimit;

    if (unitLimit < numUnits) {
      throw new IllegalArgumentException("Number of units in " + this.troopName + " exceed troop limit.");
    }
    this.numUnits = numUnits;
  }

  /*
   * Construct a BasicTroop object.
   * 
   * @param name is the name assigned to the troop.
   * 
   * @param unitLimit is the maximum amount of units that a troop can contain.
   */
  public BasicTroop(int numUnits, int unitLimit) {
    this(null, numUnits, unitLimit);
  }

  /*
   * Construct a BasicTroop object. The default number of units is 999 if numLimit
   * is not explicitly set.
   * 
   * @param numunits is the number of units put in the troop.
   */
  public BasicTroop(int numUnits) {
    this(null, numUnits, 99999);
  }

  @Override
  public boolean tryAddUnits(int toAdd) {
    if (toAdd + numUnits > unitLimit) {
      return false;
    }
    numUnits += toAdd;
    return true;
  }

  @Override
  public boolean tryRemoveUnits(int toRemove) {
    if (numUnits - toRemove < 0) {
      return false;
    }
    numUnits -= toRemove;
    return true;
  }

  @Override
  public int getNumUnits() {
    return numUnits;
  }

  @Override
  public String getName() {
    return troopName;
  }

  @Override
  public boolean trySetNumUnits(int numUnits) {
    if (numUnits > this.unitLimit) {
      return false;
    }
    this.numUnits = numUnits;
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (o != null && o.getClass().equals(getClass())) {
      BasicTroop other = (BasicTroop) o;
      return this.troopName == other.getName() && this.numUnits == other.getNumUnits();
    }
    return false;
  }

  @Override
  public String toString() {
    return this.troopName + " with " + this.numUnits + " units.\n";
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}
