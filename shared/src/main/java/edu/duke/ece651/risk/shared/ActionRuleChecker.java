package edu.duke.ece651.risk.shared;

/**
 * Abstract class to check action validity
 */
public abstract class ActionRuleChecker {
  private final ActionRuleChecker next;

  /**
   * Constructs a ActionRuleChecker
   * 
   * @param next is the ActionRuleChecker to be passed to the super class's
   *             constructor
   */
  public ActionRuleChecker(ActionRuleChecker next) {
    this.next = next;
  }

  protected abstract String checkMyRule(ActionInfo action, WorldMap worldmap);

  /**
   * Checks if all the action rules are met to perform an action on a given
   * worldmap
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkAction(ActionInfo action, WorldMap worldmap) {
    if (checkMyRule(action, worldmap) != null) {
      return checkMyRule(action, worldmap);
    }
    if (next != null) {
      return next.checkAction(action, worldmap);
    }
    return null;
  }
}
