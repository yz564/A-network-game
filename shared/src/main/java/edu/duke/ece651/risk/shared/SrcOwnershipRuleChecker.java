package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if the source territory belong to the player performing the
 * action on the current worldmap
 */
public class SrcOwnershipRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a SrcOwnershipRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public SrcOwnershipRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks ownership of the source territory on the current worldmap, conditioned
   * on if this field is present in the action info.
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
      if (!src.isBelongTo(action.getSrcOwnerName())) {
        return "That action is invalid: source Territory belongs to a different player";
      }
    }
    return null;
  }
}
