package edu.duke.ece651.risk.shared;

/*
 * A troop contains an arbitrary number of a particular kind of units. E.g. a
 * troop could contain 10 soldiers or 5 knights but not both.
 */
public interface Troop extends java.io.Serializable{
  /*
   * Add units to the troop.
   * 
   * @param toAdd is the number of units to add.
   * 
   * @returns true is adding is successful, else returns false.
   */
  public boolean tryAddUnits(int toAdd);

  /*
   * Remove units from the troop.
   * 
   * @param toRemove is the number of units to remove.
   * 
   * @returns true is removal is successful, else returns false.
   */
  public boolean tryRemoveUnits(int toRemove);

  /*
   * Return number of units in the troop.
   */
  public int getNumUnits();

  /* Return troop name.
   */
  public String getName();
  
  /* Set the number of units in a troop.
   * @param numUnits is the number of units that this troop will be assigned.  
   */
  public boolean trySetNumUnits(int numUnits);

  /**
   * Get the bonus amount of the units in the Troop.
   * @return an int that represents the bonus amount for attacking.
   */
  public int getBonus();

  /**
   * Get the tech level required for using this Troop.
   * @return an int that represents tech level required.
   */
  public int getTechLevelReq();
}













