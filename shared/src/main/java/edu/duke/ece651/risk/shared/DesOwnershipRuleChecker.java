package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if destination territory belong to a different player from
 * the source destination on the current worldmap
 */
public class DesOwnershipRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a DesOwnershipRuleChecker
   * 
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public DesOwnershipRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if the destination territory belong to a different player from the
   * source destination on the given worldmap
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    Territory des = worldmap.getTerritory(action.getDesName());
    if (des.isBelongTo(action.getSrcOwnerName())) {
      return "That action in invalid: destination Territory does not belong to a different player";
    }
    return null;
  }
}
