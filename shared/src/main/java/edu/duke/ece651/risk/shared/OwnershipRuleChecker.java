package edu.duke.ece651.risk.shared;

/**
 * Subclass to check the ownership of the source and destination of the action
 * on the current worldmap: 1) source territory belong to the player performing
 * the action. 2) destination territory belong to the same player as the source
 * territory for move action. 3) destination territory belong to a different
 * player from the source territory.
 */
public class OwnershipRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a OwnershipRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public OwnershipRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks ownership of the source and destination of the action on the current
   * worldmap: 1) source territory belong to the player performing the action. 2)
   * destination territory belong to the same player as the source territory for
   * move action. 3) destination territory belong to a different player from the
   * source territory.
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    String actionType = action.getActionType();
    Territory src = worldmap.getTerritory(action.getSrcName());
    Territory des = worldmap.getTerritory(action.getDesName());
    if (!src.isBelongTo(action.getSrcOwnerName())) {
      return "That action is invalid: source Territory belong to a different player";
    }
    if (actionType == "move") {
      if (!des.isBelongTo(action.getSrcOwnerName())) {
        return "That action in invalid: destination Territory belong to a different player";
      }
    } else if (actionType == "attack") {
      if (des.isBelongTo(action.getSrcOwnerName())) {
        return "That action in invalid: destination Territory does not belong to a different player";
      }
    }
    return null;
  }
}
