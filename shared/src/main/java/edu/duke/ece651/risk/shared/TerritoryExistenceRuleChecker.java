package edu.duke.ece651.risk.shared;

/** Subclass to check if source and destination territories exist on the current worldmap */
public class TerritoryExistenceRuleChecker extends ActionRuleChecker {

    /**
     * Constructs a TerritoryExistenceRuleChecker
     *
     * @param next is the ActionRuleChecker to be passed to the super class's constructor
     */
    public TerritoryExistenceRuleChecker(ActionRuleChecker next) {
        super(next);
    }

    /**
     * Checks if both the source and destination territories exist on the given worldmap
     *
     * @param action is the ActionInfo for an action
     * @param worldmap is the worldmap to perform the action on
     * @return error message if violates action rules, else null
     */
    @Override
    protected String checkMyRule(ActionInfo action, WorldMap worldmap) {
        // TODO: this needs to be updated to check action type, then decide to get
        // TerritoryActionInfo or UpgradeUnitActionInfo
        Territory src = worldmap.getTerritory(action.getTerritoryActionInfo().getSrcName());
        Territory des = worldmap.getTerritory(action.getTerritoryActionInfo().getDesName());
        if (src == null) {
            return "That action is invalid: source Territory does not exist";
        }
        if (des == null) {
            return "That action is invalid: destination Territory does not exist";
        }
        return null;
    }
}
