package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if upgrade levels are valid: 1) new level is valid 2)
 * current level is not at maximum 3) new level is not less or equal to current
 * level 4) for upgrading units, the new level is less than or equal to player's
 * Tech level.
 */
public class UpgradeLevelValidityRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a UpgradeLevelValidRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public UpgradeLevelValidityRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * upgrade levels are valid: 1) new level is valid 2) current level is not at
   * maximum 3) new level is not less or equal to current level 4) for upgrading
   * units, the new level is less than or equal to player's Tech level.
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    int curLevel = 0;
    int newLevel = 0;
    String actionType = action.getActionType();
    Territory src = worldmap.getTerritory(action.getSrcName());
    if (actionType == "upgrade tech") {
      curLevel = worldmap.getPlayerInfo(action.getSrcOwnerName()).getTechLevel();
      newLevel = action.getNewTechLevel();
      if (newLevel > 6) {
        return "That action is invalid: upgrade level is invalid";
      }
    }
    if (actionType == "upgrade unit") {
      if (!src.isExistTroop(action.getOldUnitLevel()) || !src.isExistTroop(action.getNewUnitLevel())) {
        return "That action is invalid: upgrade level is invalid";
      }
      curLevel = src.getTroop(action.getOldUnitLevel()).getTechLevelReq();
      newLevel = src.getTroop(action.getNewUnitLevel()).getTechLevelReq();
      int maxTechLevel = worldmap.getPlayerInfo(action.getSrcOwnerName()).getTechLevel();
      if (newLevel > maxTechLevel) {
        return "That action is invalid: upgrade level is more than maximum technology level allowed";
      }
    }
    if (curLevel == 6) {
      return "That action is invalid: current level is at maximum";
    }
    if (newLevel <= curLevel) {
      return "That action is invalid: upgrade level is not more than current level";
    }
    return null;
  }
}
