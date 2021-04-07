package edu.duke.ece651.risk.shared;

public class V1RiskWorldMap extends RiskWorldMap {
  private static final long serialVersionUID = -8601217585700186444L;

  /**
   * Construct a V1RiskWorldMap object.
   * 
   * @param names     is an array of strings of territory names
   * @param adjacency is an array of arrays of indices of adjacent territories to
   *                  each territory
   * @param groups    is an array of integers which is the initial groups id of
   *                  each territory
   */
  public V1RiskWorldMap(String[] names, int[][] adjacency, int[] groups) {
    super(names, groups);
    makeTerritories(names, adjacency);
  }

  public V1RiskWorldMap(){
    super();
  }
  /**
   * Creates the territories on the given world map
   * 
   * @param names     is the array of territories names
   * @param adjacency is the array of adjacency lists that corresponds to the
   *                  territory names
   */
  protected void makeTerritories(String[] names, int[][] adjacency) {
    for (int i = 0; i < names.length; i++) {
      Territory t = new BasicTerritory(names[i]);  
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



