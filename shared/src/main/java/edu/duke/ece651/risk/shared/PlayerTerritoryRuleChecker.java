package edu.duke.ece651.risk.shared;

import java.util.ArrayList;

/**
 * Subclass to check if the source territory belong to the player performing the
 * action on the current worldmap
 */
public class PlayerTerritoryRuleChecker extends ActionRuleChecker {

    /**
     * Constructs a PlayerTerritoryRuleChecker
     *
     * @param next is the ActionRuleChecker to be passed to the super class's
     *             constructor
     */
    public PlayerTerritoryRuleChecker(ActionRuleChecker next) {
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
        if (action.getActionType().equals("research patent")) {
            ArrayList<String> playerTerritory = action.getTargetTerritoryNames();
            for (String territoryName :playerTerritory){
                Territory target = worldmap.getTerritory(territoryName);
                if (target == null) {
                    return "That action is invalid: selected Territory does not exist";
                }
                if (!target.isBelongTo(action.getSrcOwnerName())) {
                    return "That action is invalid: selected Territory belongs to a different player";
                }
            }
        }
        return null;
    }
}
