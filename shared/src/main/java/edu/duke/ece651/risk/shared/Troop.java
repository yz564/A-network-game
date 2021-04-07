package edu.duke.ece651.risk.shared;

/**
 * A troop contains an arbitrary number of a particular kind of units. E.g. a
 * troop could contain 10 soldiers or 5 knights but not both.
 */
public interface Troop extends java.io.Serializable {

  /**
   * Return troop name.
   */
  public String getName();

  /**
   * Return number of units in the troop.
   */
  public int getNumUnits();

  /**
   * Check if the number of units is valid for a troop.
   * 
   * @param toCheck is the number of units to check if is valid for this troop.
   *
   * @return true if the number of units is valid, else returns false.
   */
  public boolean isValidUnits(int toCheck);

  /**
   * Set the number of units in a troop. Checks if the number of units is valid
   * before assignment.
   * 
   * @param numUnits is the number of units that this troop will be assigned.
   *
   * @return true if the setting is successful, else returns false.
   */
  public boolean trySetNumUnits(int numUnits);

  /**
   * Add units to the troop. Checks if the resulting unit is valid before adding.
   *
   * @param toAdd is the number of units to add.
   *
   * @return true if adding is successful, else returns false.
   */
  public boolean tryAddUnits(int toAdd);

  /**
   * Remove units from the troop. Checks if the resulting unit is valid before removing.
   *
   * @param toRemove is the number of units to remove.
   *
   * @return true if removal is successful, else returns false.
   */
  public boolean tryRemoveUnits(int toRemove);

  /**
   * Get the bonus amount of the units in the Troop.
   *
   * @return an int that represents the bonus amount for attacking.
   */
  public int getBonus();

  /**
   * Get the tech level required for using this Troop.
   *
   * @return an int that represents tech level required.
   */
  public int getTechLevelReq();

  /**
   * Get the tech cost needed to upgrade a unit from troop that is 1 level lower
   * to a unit in this troop.
   *
   * @return int that represents the total cost.
   */
  public int getTechCost();
}
