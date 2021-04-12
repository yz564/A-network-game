package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/**
 * Subclass to check if there are valid spy troops in source territory: 1) spy troop belonging to
 * action owner 2) enough spy units
 */
public class SpyValidityRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a SpyExistenceRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's constructor
   */
  public SpyValidityRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if there are spy troops belonging to action owner with enough units in source territory.
   * Violates rule if there is no spy troop belonging to action owner or not enough units.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    // If srcName and numSpyUnits is present in the action
    if (action.getSrcName() != null && action.getNumSpyUnits() != 0) {
      Territory src = worldmap.getTerritory(action.getSrcName());
      String srcOwnerName = action.getSrcOwnerName();
      int numSpyUnits = action.getNumSpyUnits();
      if (!src.isExistSpyTroop(srcOwnerName)) {
        return "That action is invalid: source Territory does not contain your spy units";
      }
      if (src.getSpyTroopNumUnits(srcOwnerName) < numSpyUnits) {
        return "That action is invalid: source Territory does not contain enough spy units";
      }
    }
    return null;
  }
}
