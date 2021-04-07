package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/**
 * Subclass to check if the troop names provided are valid troops in source
 * territory.
 */
public class TroopExistenceRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a TroopExistenceRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public TroopExistenceRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if the troop names provided are valid troops in source territory.
   * Violates rule if any of the specified troop names does not exist.
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    // If srcName and NumUnit is present in the action
    if (action.getSrcName() != null && action.getNumUnits() != null) {
      Territory src = worldmap.getTerritory(action.getSrcName());
      HashMap<String, Integer> numUnits = action.getNumUnits();
      for (String troopName : numUnits.keySet()) {
        if (!src.isExistTroop(troopName)) {
          return "That action is invalid: " + troopName + " is not a valid troop in source Territory";
        }
      }
    }
    return null;
  }

}
