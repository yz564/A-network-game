package edu.duke.ece651.risk.shared;

public class V2MapFactory implements WorldMapFactory {

  /*
   * Makes a world map with even number of territories for even number of players
   * For V1 specifically, there are 16 territories, for 2 or 4 players
   *
   * @param numPlayers is the number of players, takes values of 2 or 4/
   * 
   * @returns a World Map object
   */
  private WorldMap makeEvenMap(int numPlayers) {
    String[] names = { "Fuqua", "Law", "Gross Hall", "FFRC", "Bryan Center", "LSRC", "Pratt", "Perkins Library",
        "Duke Hospital", "Duke Clinics", "Duke Garden", "Duke Chapel", "Student Housing", "Wilson Gym",
        "Cameron Stadium", "Wallace Stadium" };
    int[][] adjacency = { { 1, 2, 14 }, { 0, 2, 4, 14 }, { 0, 1, 3, 4, 5 }, { 2, 4, 5, 6 }, { 1, 2, 3, 6, 11, 12, 14 },
        { 2, 3, 6, 8 }, { 3, 4, 5, 7, 8, 9, 11 }, { 6, 9, 10, 11 }, { 5, 6, 9 }, { 6, 7, 8, 10 }, { 7, 9, 11, 12 },
        { 4, 6, 7, 10, 12 }, { 4, 10, 11, 13, 14 }, { 12, 14, 15 }, { 0, 1, 4, 12, 13, 15 }, { 14, 15 } };
    int[][] patentResearchRates = { {1, 5, 3, 1, 1}, {3, 4, 1, 1, 1}, {2, 2, 3, 3, 1}, {3, 1, 2, 1, 1}, {3, 2, 2, 2, 1},
            {2, 1, 3, 3, 1}, {1, 1, 5, 2, 1}, {5, 5, 5, 5, 1}, {1, 1, 2, 5, 2}, {1, 1, 1, 5, 1}, {2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3}, {3, 3, 3, 3, 4}, {2, 2, 2, 2, 5}, {1, 1, 1, 1, 5}, {1, 1, 1, 1, 5} };
    int[] sizes = { 1, 2, 3, 1, 3, 2, 3, 2, 1, 1, 2, 3, 2, 2, 3, 1};
    if (numPlayers == 4) {
      int[] groups = { 1, 3, 1, 1, 1, 4, 4, 4, 4, 2, 2, 2, 2, 3, 3, 3 };
      return new V2RiskWorldMap(names, adjacency, patentResearchRates, groups, sizes);
    } else {
      int[] groups = { 1, 2, 1, 1, 2, 2, 1, 1, 2, 2, 2, 2, 2, 1, 1, 1 };
      return new V2RiskWorldMap(names, adjacency, patentResearchRates, groups, sizes);
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
    String[] names = { "Fuqua", "Law", "Gross Hall", "FFRC", "Bryan Center", "LSRC", "Pratt", "Perkins Library",
        "Duke Hospital", "Duke Clinics", "Duke Garden", "Duke Chapel", "Student Housing", "Wilson Gym",
        "Cameron Stadium" };
    int[][] adjacency = { { 1, 2, 14 }, { 0, 2, 4, 14 }, { 0, 1, 3, 4, 5 }, { 2, 4, 5, 6 }, { 1, 2, 3, 6, 11, 12, 14 },
        { 2, 3, 6, 8 }, { 3, 4, 5, 7, 8, 9, 11 }, { 6, 9, 10, 11 }, { 5, 6, 9 }, { 6, 7, 8, 10 }, { 7, 9, 11, 12 },
        { 4, 6, 7, 10, 12 }, { 4, 10, 11, 13, 14 }, { 12, 14 }, { 0, 1, 4, 12, 13 } };
    int[][] patentResearchRates = { {1, 5, 3, 1, 1}, {3, 4, 1, 1, 1}, {2, 2, 3, 3, 1}, {3, 1, 2, 1, 1}, {3, 2, 2, 2, 1},
            {2, 1, 3, 3, 1}, {1, 1, 5, 2, 1}, {5, 5, 5, 5, 1}, {1, 1, 2, 5, 2}, {1, 1, 1, 5, 1}, {2, 2, 2, 2, 2},
            {3, 3, 3, 3, 3}, {3, 3, 3, 3, 4}, {2, 2, 2, 2, 5}, {1, 1, 1, 1, 5} };
    int[] sizes = { 1, 2, 3, 1, 3, 2, 3, 2, 1, 1, 2, 3, 2, 1, 3 };
    if (numPlayers == 5) {
      int[] groups = { 1, 1, 2, 4, 1, 2, 4, 4, 2, 5, 5, 5, 3, 3, 3 };
      return new V2RiskWorldMap(names, adjacency, patentResearchRates, groups, sizes);
    } else {
      int[] groups = { 1, 1, 2, 1, 1, 2, 2, 3, 2, 2, 3, 1, 3, 3, 3 };
      return new V2RiskWorldMap(names, adjacency, patentResearchRates, groups, sizes);
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
    int[][] patentResearchRates = { {1, 5, 3, 1, 1}, {3, 4, 1, 1, 1}, {2, 2, 3, 3, 1}, {3, 1, 2, 1, 1}, {3, 2, 2, 2, 1},
            {2, 1, 3, 3, 1}, {1, 1, 5, 2, 1}, {1, 1, 5, 2, 1}, {1, 1, 5, 2, 1} };
    int[] groups = { 1, 1, 1, 2, 2, 2, 3, 3, 3 };
    int[] sizes = {1, 3, 2, 2, 1, 3, 1, 3, 2};
    return new V2RiskWorldMap(names, adjacency, patentResearchRates, groups, sizes);
  }
}
