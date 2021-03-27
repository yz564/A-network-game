package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if source territory is valid (i.e. the territory belongs to
 * the current player and there are enough units to perform the action)
 */
public class SrcValidityRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a SrcValidityRuleChecker
   * 
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public SrcValidityRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if the source territory is valid to perform the action on the given
   * worldmap: 1. source territory belongs to the player who triggered the action
   * 2. there are enough units in the source territory to perform the action
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    Territory src = worldmap.getTerritory(action.getSrcName());
    if (!src.isBelongTo(action.getSrcOwnerName())) {
      return "That action is invalid: source Territory belong to a different player";
    }
    if (src.getTroopNumUnits("Basic") < action.getUnitNum()) {
      return "That action is invalid: source Territory does not contain enough units";
    }
    return null;
  }
}
