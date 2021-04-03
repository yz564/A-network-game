package edu.duke.ece651.risk.shared;

public class ActionRuleCheckerHelper {
  private final ActionRuleChecker moveChecker;
  private final ActionRuleChecker attackChecker;
  private final ActionRuleChecker upgradeUnitChecker;
  private final ActionRuleChecker upgradeTechChecker;

  /**
   * Constructs an ActionRuleCheckerHelper, by initializing a ActionRuleChecker
   * for each type of actions via respective rule checker chains.
   */
  public ActionRuleCheckerHelper() {
    this.moveChecker = new TerritoryExistenceRuleChecker(new SrcOwnershipRuleChecker(
        new TroopExistenceRuleChecker(new EnoughUnitsRuleChecker(new DesReachableRuleChecker(null)))));
    this.attackChecker = new TerritoryExistenceRuleChecker(new SrcOwnershipRuleChecker(new TroopExistenceRuleChecker(
        new EnoughUnitsRuleChecker(new DesOwnershipRuleChecker(new DesAdjacencyRuleChecker(null))))));
    this.upgradeUnitChecker = new TerritoryExistenceRuleChecker(new SrcOwnershipRuleChecker(
        new TroopExistenceRuleChecker(new EnoughUnitsRuleChecker(new UpgradeUnitRuleChecker(null)))));
    this.upgradeTechChecker = new UpgradeTechRuleChecker(null);
  }

  /**
   * Checks if all the action rules are met to perform a move action on a given
   * worldmap. Calls the checkMyRule() of moveChecker.
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForMove(ActionInfo action, WorldMap worldmap) {
    return this.moveChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an attack action on a given
   * worldmap. Calls the checkMyRule() of attackChecker.
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForAttack(ActionInfo action, WorldMap worldmap) {
    return this.attackChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade unit action on a
   * given worldmap. Calls the checkMyRule() of upgradeUnitChecker.
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForUpgradeUnit(ActionInfo action, WorldMap worldmap) {
    return this.upgradeUnitChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade tech action on a
   * given worldmap. Calls the checkMyRule() of upgradeTechChecker.
   * 
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForUpgradeTech(ActionInfo action, WorldMap worldmap) {
    return this.upgradeTechChecker.checkAction(action, worldmap);
  }
}
