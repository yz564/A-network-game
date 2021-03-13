package edu.duke.ece651.risk.shared;

public class V1MapFactory implements WorldMapFactory {

  /*
   * Creates the territories on the given world map
   * 
   * @param names is the array of territories names
   * 
   * @param adjacency is the array of adjacency lists that corresponds to the
   * territory names
   */
  private void makeTerritories(WorldMap worldmap, String[] names, int[][] adjacency) {
    for (int i = 0; i < names.length; i++) {
      worldmap.tryAddTerritory(names[i]);
    }
    for (int i = 0; i < names.length; i++) {
      for (int j = 0; j < adjacency[i].length; j++) {
        Territory t = worldmap.getTerritory(names[i]);
        Territory n = worldmap.getTerritory(names[adjacency[i][j]]);
        t.tryAddNeighbor(n);
      }
    }
  }

  /*
   * Creates the initial grouping of territories on the given world map
   * 
   * @param names is the array of territories names
   * 
   * @param groups is the array of initial grouping number that corresponds to the
   * territory names
   */
  private void makeInitGroups(WorldMap worldmap, String[] names, int[] groups) {
    for (int i = 0; i < names.length; i++) {
      worldmap.tryAddInitGroup(groups[i], names[i]);
    }
  }

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
    WorldMap worldmap = new RiskWorldMap();
    makeTerritories(worldmap, names, adjacency);
    if (numPlayers == 4) {
      int[] groups = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 2, 4, 1 };
      makeInitGroups(worldmap, names, groups);
    } else {
      int[] groups = { 1, 1, 1, 2, 2, 1, 1, 1, 1, 2, 2, 2, 2, 2, 1, 1 };
      makeInitGroups(worldmap, names, groups);
    }
    return worldmap;
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
    WorldMap worldmap = new RiskWorldMap();
    makeTerritories(worldmap, names, adjacency);
    if (numPlayers == 5) {
      int[] groups = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5 };
      makeInitGroups(worldmap, names, groups);
    } else {
      int[] groups = { 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 1, 1 };
      makeInitGroups(worldmap, names, groups);
    }
    return worldmap;
  }

  @Override
  public WorldMap makeWorldMap(int numPlayers) {
    if (numPlayers % 2 == 1) {
      return makeOddMap(numPlayers);
    } else {
      return makeEvenMap(numPlayers);
    }
  }
}
