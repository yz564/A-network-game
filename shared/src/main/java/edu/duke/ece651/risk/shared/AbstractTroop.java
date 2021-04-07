package edu.duke.ece651.risk.shared;

public abstract class AbstractTroop implements Troop {
  private static final long serialVersionUID = -1788807224514071854L;
  protected final String troopName;
  protected final int unitLimit;
  protected int numUnits;

  /**
   * Constructs a AbstractTroop object.
   * 
   * @param name      is the name assigned to the troop.
   * @param numUnits  is the number of units put in the troop.
   * @param unitLimit is the maximum amount of units that a troop can contain.
   */
  public AbstractTroop(String name, int numUnits, int unitLimit) {
    this.troopName = name;
    if (numUnits < 0) {
      throw new IllegalArgumentException("A troop cannot have negative number of units.");
    }
    this.unitLimit = unitLimit;

    if (unitLimit < numUnits) {
      throw new IllegalArgumentException("Number of units in " + this.troopName + " exceed troop limit.");
    }
    this.numUnits = numUnits;
  }

  @Override
  public String getName() {
    return troopName;
  }

  @Override
  public int getNumUnits() {
    return numUnits;
  }

  @Override
  public boolean isValidUnits(int toCheck) {
    if (toCheck < 0 || toCheck > unitLimit) {
      return false;
    }
    return true;
  }

  @Override
  public boolean tryAddUnits(int toAdd) {
    if (!isValidUnits(toAdd + numUnits)) {
      return false;
    }
    numUnits += toAdd;
    return true;
  }

  @Override
  public boolean tryRemoveUnits(int toRemove) {
    if (!isValidUnits(numUnits - toRemove)) {
      return false;
    }
    numUnits -= toRemove;
    return true;
  }

  @Override
  public boolean trySetNumUnits(int numUnits) {
    if (!isValidUnits(numUnits)) {
      return false;
    }
    this.numUnits = numUnits;
    return true;
  }
}
