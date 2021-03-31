package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public class BasicTerritory extends AbstractTerritory {
  private static final long serialVersionUID = -8815409601117401416L;

  /**
   * Makes initial troops for a new territory. Pass makeTroops() in constructor of
   * basic territory.
   *
   * @return a HashMap with String key as the Troop name, and Troop object value.
   */
  static HashMap<String, Troop> makeTroops(int numUnits) {
    HashMap<String, Troop> myTroops = new HashMap<>();
    myTroops.put("Basic", new BasicTroop(numUnits));
    return myTroops;
  }

  /**
   * Construct a BasicTerritory object.
   *
   * @param name   is the name to assign to the territory.
   * @param troops are the troops that are present in this territory. HashMap key
   *               is troop name, and value is the Troop objects representing
   *               troops in this territory.
   */
  public BasicTerritory(String name, HashMap<String, Troop> troops) {
    super(name, troops);
  }

  /**
   * Construct a BasicTerritory object with default Troops made by makeTroops(),
   * taking numUnits as a parameter.
   *
   * @param name     is the name to assign to the territory.
   * @param numUnits is the number of basic units to assign to this territory.
   */
  public BasicTerritory(String name, int numUnits) {
    this(name, makeTroops(numUnits));
  }

  /**
   * Construct a BasicTerritory object with default Troops set as Basic Troop with
   * 0 units.
   *
   * @param name is the name to assign to the territory.
   */
  public BasicTerritory(String name) {
    this(name, 0);
  }

  @Override
  public boolean equals(Object o) {
    if (o != null && o.getClass().equals(getClass())) {
      BasicTerritory other = (BasicTerritory) o;
      return this.myTroops.equals(other.getMyTroops()) && this.territoryName.equals(other.getName())
          && this.myNeighbors.equals(other.getMyNeighbors());
    }
    return false;
  }

  @Override
  public String toString() {
    return "Territory " + this.territoryName + " contains the following troop:\n" + myTroops.toString()
        + " and is adjacent to the following territories:\n" + myNeighbors.keySet().toString();
  }

  @Override
  public int hashCode() {
    return toString().hashCode();
  }

  @Override
  public int getSize() {
    return 0;
  }

  @Override
  public HashMap<String, Integer> getResProduction() {
    return null;
  }

  @Override
  public int getMoveCost(Territory neighbor) {
    return 0;
  }

  @Override
  public int findMinMoveCost(Territory toReach) {
    return 0;
  }
}











