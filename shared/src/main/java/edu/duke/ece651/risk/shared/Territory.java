package edu.duke.ece651.risk.shared;

/*
 * Represents an interface for classes that would 
 * represent territories in a RISK Map.
 *
 * Each Territory contains a number of units that
 * could be used to either attack or defend in a combat.
 */
public interface Territory {
  /*
   * Adds units to the territory.
   * 
   * @param toAdd is the number of units to add.
   * 
   * @returns true if add is successful, false if not.
   */
  public boolean tryAddUnits(int toAdd);

  /*
   * Remove units from the territory.
   * 
   * @param toRemove is the number of units to remove.
   *
   * @returns true if unit is removed is successful, false if not.
   */
  public boolean tryRemoveUnits(int toRemove);

  /*
   * Returns the total number of units inside a territory.
   */
  public int getNumUnits();

  /* Return territory name.
   */
  public String getName();
  
  /* Set number of units present in a territory.
   * @param numUnits is the number of units that is set for this terriroty..
   */
  public boolean trySetNumUnits(int numUnits);
}













