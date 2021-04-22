package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if cloaking can be performed, i.e cloaking has already been researched
 */
public class CloakingRuleChecker extends ActionRuleChecker {

    /**
     * Constructs a CloakingRuleChecker
     *
     * @param next is the ActionRuleChecker to be passed to the super class's constructor
     */
    public CloakingRuleChecker(ActionRuleChecker next) {
        super(next);
    }

    /**
     * Checks if cloaking can be performed, i.e cloaking has already been researched
     *
     * @param action is the ActionInfo for an action
     * @param worldmap is the worldmap to perform the action on
     * @return error message if violates action rules, else null
     */
    @Override
    protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
        if (action.getActionType().equals("cloaking")){
            PlayerInfo playerInfo = worldmap.getPlayerInfo(action.getSrcOwnerName());
            if (!playerInfo.getIsCloakingResearched()) {
                return "That action is invalid: cloaking has not been researched yet";
            }
        }
        return null;
    }
}
