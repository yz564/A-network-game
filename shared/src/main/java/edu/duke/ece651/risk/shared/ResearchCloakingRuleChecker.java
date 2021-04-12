package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if cloaking can be researched: 1) cloaking has not already been researched 2)
 * current tech level is high enough, by calling PlayerInfo.canCloakingResearched()
 */
public class ResearchCloakingRuleChecker extends ActionRuleChecker {

  /**
   * Constructs a ResearchCloakingRuleChecker
   *
   * @param next is the ActionRuleChecker to be passed to the super class's constructor
   */
  public ResearchCloakingRuleChecker(ActionRuleChecker next) {
    super(next);
  }

  /**
   * Checks if cloaking can be researched: 1) cloaking has not already been researched 2) * current
   * tech level is high enough, by calling PlayerInfo.canCloakingResearched()
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  @Override
  protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
    if (action.getActionType().equals("research cloaking")){
      PlayerInfo playerInfo = worldmap.getPlayerInfo(action.getSrcOwnerName());
      if (playerInfo.getIsCloakingResearched()) {
        return "That action is invalid: cloaking has already been researched";
      }
      if (!playerInfo.canCloakingResearched()) {
        return "That action is invalid: current tech level is not high enough to research cloaking";
      }
    }
    return null;
  }
}
