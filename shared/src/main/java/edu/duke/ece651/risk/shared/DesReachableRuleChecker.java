package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if destination territory is reachable from the source destination on the
 * current worldmap (i.e there is a connected path of adjacent territories belong to the same
 * player)
 */
public class DesReachableRuleChecker extends ActionRuleChecker {
    /**
     * Constructs a DesOwnershipRuleChecker
     *
     * @param next is the ActionRuleChecker to be passed to the super class's constructor
     */
    public DesReachableRuleChecker(ActionRuleChecker next) {
        super(next);
    }

    /**
     * Checks if the destination territory is reachable from the source destination on the given
     * worldmap
     *
     * @param action is the ActionInfo for an action
     * @param worldmap is the worldmap to perform the action on
     * @return error message if violates action rules, else null
     */
    @Override
    protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
        Territory src = worldmap.getTerritory(action.getTerritoryActionInfo().getSrcName());
        Territory des = worldmap.getTerritory(action.getTerritoryActionInfo().getDesName());
        if (!src.isReachableTo(des)) {
            return "That action is invalid: destination Territory is not reachable from source Territory";
        }
        return null;
    }
}
