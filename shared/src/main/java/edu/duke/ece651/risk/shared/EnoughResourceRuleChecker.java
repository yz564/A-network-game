package edu.duke.ece651.risk.shared;

import java.util.HashMap;

/**
 * Subclass to check if source territory contains enough units of specified
 * troop name to perform the action.
 */
public class EnoughResourceRuleChecker extends ActionRuleChecker {

    /**
     * Constructs a EnoughResourceRuleChecker
     *
     * @param next is the ActionRuleChecker to be passed to the super class's
     *             constructor
     */
    public EnoughResourceRuleChecker(ActionRuleChecker next) {
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
        ActionCostCalculator calculator = new ActionCostCalculator();
        HashMap<String, Integer> costs = calculator.calculateCost(action, worldmap);
        HashMap<String, Integer> resources = worldmap.getPlayerInfo(action.getSrcOwnerName()).getResTotals();
        for (String res : costs.keySet()) {
            if (resources.get(res) < costs.get(res)) {
                return "That action is invalid: you do not have enough resources to perform the action.";
            }
        }
        return null;
    }
}