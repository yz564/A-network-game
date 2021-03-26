package edu.duke.ece651.risk.shared;

public class V2RiskWorldMap extends RiskWorldMap {
  private static final long serialVersionUID = -8601217585700186444L;

  public V2RiskWorldMap(String[] names, int[][] adjacency, int[] groups, int[] size) {
    super(names, groups);
    makeTerritories(names, adjacency, size);
  }

  /**
   * Adds a new territory with a given name on the world map. Territory is created
   * with no troops, neighbors or owner.
   * 
   * @param toAdd is the name of the territory to add
   *
   * @return true if add is successful, false if not.
   */
  public boolean tryAddTerritory(String toAdd, Integer foodProduction, Integer techProduction) {
    if (myTerritories.keySet().contains(toAdd)) {
      return false;
    } else {
      myTerritories.put(toAdd, new BasicV2Territory(toAdd, foodProduction, techProduction));
      return true;
    }
  }

  protected void makeTerritories(String[] names, int[][] adjacency, int[] size) {
    for (int i = 0; i < names.length; i++) {
      this.tryAddTerritory(names[i], size[i] * 5, size[i] * 2);
    }
    for (int i = 0; i < names.length; i++) {
      for (int j = 0; j < adjacency[i].length; j++) {
        Territory t = this.getTerritory(names[i]);
        Territory n = this.getTerritory(names[adjacency[i][j]]);
        t.tryAddNeighbor(n);
      }
    }
  }
}
