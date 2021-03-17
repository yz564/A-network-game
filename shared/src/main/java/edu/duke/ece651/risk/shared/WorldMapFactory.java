package edu.duke.ece651.risk.shared;

/*
 * Represents an interface for the creation of world map
 * in a RISK game.
 *
 * There are 4 map designs, each for different number of
 * players from 2 to 5
 */
public interface WorldMapFactory {

  /*
   * Makes a world map for a given number of players
   * 
   * @param numPlayers is the number of players to design the map for
   * 
   * @returns the World Map object
   */
  public WorldMap makeWorldMap(int numPlayes);

  public WorldMap makeTestWorldMap();
}





