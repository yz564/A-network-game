package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if the new tech level to upgrade is valid, by calling
 * PlayerInfo.isValidTechLevel()
 */
public class UpgradeTechRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a UpgradeTechRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public UpgradeTechRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if the new tech level to upgrade is valid, by calling
   * PlayerInfo.isValidTechLevel()
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    // If newTechLevel is present in action info
    if (action.getNewTechLevel() != 0) {
      PlayerInfo playerInfo = worldmap.getPlayerInfo(action.getSrcOwnerName());
      int newLevel = action.getNewTechLevel();
      if (!playerInfo.isValidTechLevel(newLevel)) {
        return "That action is invalid: new tech level is invalid";
      }
    }
    return null;
  }
}
