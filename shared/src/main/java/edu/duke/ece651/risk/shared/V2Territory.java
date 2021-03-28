package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public class V2Territory extends AbstractTerritory {
  private static final long serialVersionUID = -8815409601117401416L;
  private final HashMap<String, Integer> resProduction;
  private final int size;

  /**
   * Makes initial troops for a new territory. Pass makeTroops() in constructor of
   * V2 territory.
   *
   * @return a HashMap with String key as the Troop name, and Troop object value.
   */
  static HashMap<String, Troop> makeTroops() {
    HashMap<String, Troop> myTroops = new HashMap<>();
    myTroops.put("level0", new LevelTroop("level0", 0, 0, 0, 0));
    myTroops.put("level1", new LevelTroop("level1", 0, 1, 1, 3));
    myTroops.put("level2", new LevelTroop("level2", 0, 3, 2, 8));
    myTroops.put("level3", new LevelTroop("level3", 0, 5, 3, 19));
    myTroops.put("level4", new LevelTroop("level4", 0, 8, 4, 25));
    myTroops.put("level5", new LevelTroop("level5", 0, 11, 5, 35));
    myTroops.put("level6", new LevelTroop("level6", 0, 15, 6, 50));
    return myTroops;
  }

  /**
   * Construct a V2Territory object.
   *
   * @param name      is the name to assign to the territory.
   * @param troops    are the troops that are present in this territory. HashMap
   *                  key is troop name, and value is the Troop objects
   *                  representing troops in this territory.
   * @param resources is the production rate of different types of resources.
   *                  HashMap key is the resource name, and value is the
   *                  production rate.
   * @param size      is an integer represents the size of the territory.
   */
  public V2Territory(String name, HashMap<String, Troop> troops, HashMap<String, Integer> resources, int size) {
    super(name, troops);
    this.resProduction = resources;
    this.size = size;
  }

  /**
   * Construct a V2Territory object with default Troops made by makeTroops().
   *
   * @param name      is the name to assign to the territory.
   * @param resources is the production rate of different types of resources.
   *                  HashMap key is the resource name, and value is the
   *                  production rate.
   * @param size      is an integer represents the size of the territory.
   */
  public V2Territory(String name, HashMap<String, Integer> resources, int size) {
    this(name, makeTroops(), resources, size);
  }

  /**
   * Construct a V2Territory object with default Troops made by makeTroops(), and
   * given food and tech production rate.
   *
   * @param name           is the name to assign to the territory.
   * @param foodProduction is the int represents the foodProduction rate of this
   *                       territory.
   * @param techProduction is the int represents the techProduction rate of this
   *                       territory.
   * @param size           is an integer represents the size of the territory.
   */
  public V2Territory(String name, Integer foodProduction, Integer techProduction, int size) {
    this(name, new HashMap<>(), size);
    resProduction.put("food", foodProduction);
    resProduction.put("tech", techProduction);
  }

  @Override
  public int getSize() {
    return size;
  }

  @Override
  public HashMap<String, Integer> getResProduction() {
    return this.resProduction;
  }
}
