package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if source and destination territories exist on the current
 * worldmap
 */
public class TerritoryExistenceRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a TerritoryExistenceRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public TerritoryExistenceRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if both the source and destination territories exist on the given
   * worldmap, conditioned on if these fields are present in the action info.
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    // If srcName is present in the action
    if (action.getSrcName() != null) {
      Territory src = worldmap.getTerritory(action.getSrcName());
      if (src == null) {
        return "That action is invalid: source Territory does not exist";
      }
    }
    // If desName is present in the action
    if (action.getDesName() != null) {
      Territory des = worldmap.getTerritory(action.getDesName());
      if (des == null) {
        return "That action is invalid: destination Territory does not exist";
      }
    }
    return null;
  }
}
