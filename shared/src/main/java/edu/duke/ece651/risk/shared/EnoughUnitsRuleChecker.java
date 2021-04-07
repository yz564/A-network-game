package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/**
 * Subclass to check if source territory contains enough units of specified
 * troop name to perform the action.
 */
public class EnoughUnitsRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a EnoughUnitsRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public EnoughUnitsRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if the source territory contains enough units of specified troop name
   * in the source territory to perform the action. Violates rule if any of the
   * specified troop names does not contain enough units.
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
        if (src.getTroopNumUnits(troopName) < numUnits.get(troopName)) {
          return "That action is invalid: source Territory does not contain enough " + troopName + " units";
        }
      }
    }
    return null;
  }
}
