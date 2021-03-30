package edu.duke.ece651.risk.shared;

/**
 * Subclass to check if destination territory is adjacent to the source territory on the current
 * worldmap
 */
public class DesAdjacencyRuleChecker extends ActionRuleChecker {

    /**
     * Constructs a DesAdjacencyRuleChecker
     *
     * @param next is the ActionRuleChecker to be passed to the super class's constructor
     */
    public DesAdjacencyRuleChecker(ActionRuleChecker next) {
        super(next);
    }

    /**
     * Checks if the destination territory is adjacent to the source territory on the given worldmap
     *
     * @param action is the ActionInfo for an action
     * @param worldmap is the worldmap to perform the action on
     * @return error message if violates action rules, else null
     */
    @Override
    protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
        Territory src = worldmap.getTerritory(action.getTerritoryActionInfo().getSrcName());
        Territory des = worldmap.getTerritory(action.getTerritoryActionInfo().getDesName());
        if (!src.isAdjacentTo(des)) {
            return "That action is invalid: destination Territory is not adjacent to source Territory";
        }
        return null;
    }
}
