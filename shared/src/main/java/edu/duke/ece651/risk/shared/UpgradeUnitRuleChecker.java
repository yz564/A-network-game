package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if the new unit level to upgrade is valid: 1) the new unit
 * level less than or equals to player's tech level 2) the new unit level is
 * more than the current unit level
 */
public class UpgradeUnitRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a UpgradeUnitRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public UpgradeUnitRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Check if the new unit level to upgrade is valid: 1) the new unit level is
   * player's tech level 2) the new unit level is more than the current unit level
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    // If srcName, oldUnitLevel, newUnitLevel is present in action info
    if (action.getSrcName() != null && action.getOldUnitLevel() != null && action.getNewUnitLevel() != null) {
      PlayerInfo playerInfo = worldmap.getPlayerInfo(action.getSrcOwnerName());
      Territory src = worldmap.getTerritory(action.getSrcName());
      int curLevel = src.getTroop(action.getOldUnitLevel()).getTechLevelReq();
      int newLevel = src.getTroop(action.getNewUnitLevel()).getTechLevelReq();
      if (!playerInfo.isValidUnitLevel(newLevel)) {
        return "That action is invalid: new troop level is invalid";
      }
      if (newLevel <= curLevel) {
        return "That action is invalid: new troop level is less than current troop level";
      }
    }
    return null;
  }
}
