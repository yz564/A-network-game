package edu.duke.ece651.risk.shared;

public class V1MapFactory implements WorldMapFactory {

  /*
   * Makes a world map with even number of territories for even number of players
   * For V1 specifically, there are 16 territories, for 2 or 4 players
   *
   * @param numPlayers is the number of players, takes values of 2 or 4/
   * 
   * @returns a World Map object
   */
  private WorldMap makeEvenMap(int numPlayers) {
    String[] names = { "Braavosian Coastlands", "Hills of Horvos", "Forest of Qohor", "Myr", "Lower Rnoyne", "Mantarys",
        "Lhaxar", "Old Ohis", "The Red Waste", "Bayasabhad", "Northern Jade Sea", "Vaes Dothrak",
        "Eastern Dothraki Sea", "Western Dothraki Sea", "Northern Dothraki Sea", "Sarnor" };
    int[][] adjacency = { { 1, 3 }, { 0, 2, 3, 4 }, { 1, 4, 13, 15 }, { 0, 1, 4 }, { 1, 2, 3, 5, 13 }, { 4, 6, 13 },
        { 5, 7, 8, 12, 13 }, { 6, 8 }, { 6, 7, 9, 10, 12 }, { 8, 10 }, { 8, 9, 11, 12 }, { 10, 12 },
        { 6, 8, 10, 11, 13, 14 }, { 2, 4, 5, 6, 12, 14, 15 }, { 12, 13, 15 }, { 2, 13, 14 } };
    if (numPlayers == 4) {
      int[] groups = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 2, 4, 1 };
      return new V1RiskWorldMap(names, adjacency, groups);
    } else {
      int[] groups = { 1, 1, 1, 2, 2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 1, 1 };
      return new V1RiskWorldMap(names, adjacency, groups);
    }
  }

  /*
   * Makes a world map with odd number of territories for odd number of players
   * For V1 specifically, there are 15 territories, for 3 or 5 players
   *
   * @param numPlayers is the number of players, takes values of 3 or 5/
   * 
   * @returns a World Map object
   */
  private WorldMap makeOddMap(int numPlayers) {
    String[] names = { "Braavosian Coastlands", "Hills of Horvos", "Forest of Qohor", "Myr", "Lower Rnoyne", "Mantarys",
        "Lhaxar", "Old Ohis", "The Red Waste", "Bayasabhad", "Northern Jade Sea", "Vaes Dothrak",
        "Eastern Dothraki Sea", "Western Dothraki Sea", "Northern Dothraki Sea" };
    int[][] adjacency = { { 1, 3 }, { 0, 2, 3, 4 }, { 1, 4, 13, 14 }, { 0, 1, 4 }, { 1, 2, 3, 5, 13 }, { 4, 6, 13 },
        { 5, 7, 8, 12, 13 }, { 6, 8 }, { 6, 7, 9, 10, 12 }, { 8, 10 }, { 8, 9, 11, 12 }, { 10, 12 },
        { 6, 8, 10, 11, 13, 14 }, { 2, 4, 5, 6, 12, 14 }, { 2, 12, 13 } };
    if (numPlayers == 5) {
      int[] groups = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5 };
      return new V1RiskWorldMap(names, adjacency, groups);
    } else {
      int[] groups = { 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 1, 1 };
      return new V1RiskWorldMap(names, adjacency, groups);
    }
  }

  @Override
  public WorldMap makeWorldMap(int numPlayers) {
    if (numPlayers % 2 == 1) {
      return makeOddMap(numPlayers);
    } else {
      return makeEvenMap(numPlayers);
    }
  }

  /*
   * Makes a testing world map for 3 players which follows the design in the
   * instructions
   * 
   * @returns a World Map object
   */
  public WorldMap makeTestWorldMap() {
    String[] names = { "Narnia", "Midkemia", "Oz", "Elantris", "Roshar", "Scadrial", "Gondor", "Mordor", "Hogwarts" };
    int[][] adjacency = { { 1, 3 }, { 0, 2, 3, 4 }, { 1, 5, 6, 7 }, { 0, 1, 4, 5 }, { 3, 5, 8 }, { 1, 2, 3, 4, 7, 8 },
        { 2, 7 }, { 2, 5, 6, 8 }, { 4, 5, 7 } };
    int[] groups = { 1, 1, 1, 2, 2, 2, 3, 3, 3 };
    return new V1RiskWorldMap(names, adjacency, groups);
  }
}












