package edu.duke.ece651.risk.shared;

public class V2RiskWorldMap extends RiskWorldMap {
  private static final long serialVersionUID = -8601217585700186444L;

  /**
   * Construct a V2RiskWorldMap object.
   * 
   * @param names     is an array of strings of territory names
   * @param adjacency is an array of arrays of indices of adjacent territories to
   *                  each territory
   * @param groups    is an array of integers which is the initial groups id of
   *                  each territory
   * @param size      is an array of integers which is the territory sizes
   */
  public V2RiskWorldMap(String[] names, int[][] adjacency, int[] groups, int[] size) {
    super(names, groups);
    makeTerritories(names, adjacency, size);
  }

  public V2RiskWorldMap(){
    super();
  }
  /**
   * Creates the territories on the given world map
   * 
   * @param names     is the array of territories names
   * @param adjacency is the array of adjacency lists that corresponds to the
   *                  territory names
   * @param size      is the array of territory sizes
   */
  protected void makeTerritories(String[] names, int[][] adjacency, int[] size) {
    for (int i = 0; i < names.length; i++) {
      Territory t = new V2Territory(names[i], size[i] * 5, size[i] * 2, size[i]);
      this.tryAddTerritory(t);
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
