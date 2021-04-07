package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if the destination territroy belong to a different player
 * on the current worldmap
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
   * Checks ownership of the destination territory on the current worldmap,
   * conditioned on if this field is present inthe action info
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    // If desName is present in the action
    if (action.getDesName() != null) {
      Territory des = worldmap.getTerritory(action.getDesName());
      if (des.isBelongTo(action.getSrcOwnerName())) {
        return "That action is invalid: destination Territory does not belong to a different player";
      }
    }
    return null;
  }
}
