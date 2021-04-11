package edu.duke.ece651.risk.shared;

public class SpyTroop extends AbstractTroop {
  private static final long serialVersionUID = -178880722451421432L;

  /** Required tech level for using this troop. */
  private final int techLevelReq;

  /** The attack bonus amount the units in this troop have. */
  private final int bonus;

  /**
   * The cost needed to upgrade a unit from troop that is 1 level lower to a unit
   * in this troop.
   */
  private final int techCost;

  /**
   * SpyTroop can travel to enemy's territory, so it should be initialized with an ownerName (May change owner so not final)
   */
  private String ownerName;
  /**
   * Constructs a SpyTroop object.
   *
   * @param name is the name assigned to the troop.
   * @param numUnits is the number of units put in the troop.
   * @param unitLimit is the maximum amount of units that a troop can contain.
   * @param bonus is the bonus for units in the Troop.
   * @param techCost is the cost needed to upgrade a unit from troop that is 1
   * level lower to a unit in this troop.
   */
  public SpyTroop(String name, int numUnits, int unitLimit, int bonus, int techLevelReq, int techCost,String ownerName) {
    super(name, numUnits, unitLimit);
    this.techLevelReq = techLevelReq;
    this.bonus = bonus;
    this.techCost = techCost;
    this.ownerName=ownerName;
  }

  /**
   * Constructs a SpyTroop object with unitLimit assigned with 99999.
   *
   * @param numUnits is the number of units put in the troop.
   * @param ownerName is the player name who own this troop.
   */
  public SpyTroop(int numUnits, String ownerName) {
    this("Spy", numUnits, 99999, 0, 0, 20, ownerName);
  }
  
  public String getOwnerName(){
    return this.ownerName;
  }
  public void changeOwner(String newOwner){
    this.ownerName=newOwner;
  }

  @Override
  public int getTechLevelReq() {
    return techLevelReq;
  }

  @Override
  public int getTechCost() {
    return techCost;
  }

  @Override
  public int getBonus() {
    return bonus;
  }
}
