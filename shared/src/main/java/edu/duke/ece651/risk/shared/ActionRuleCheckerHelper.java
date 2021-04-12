package edu.duke.ece651.risk.shared;

public class ActionRuleCheckerHelper {
  private final ActionRuleChecker moveChecker;
  private final ActionRuleChecker attackChecker;
  private final ActionRuleChecker upgradeUnitChecker;
  private final ActionRuleChecker upgradeTechChecker;
  private final ActionRuleChecker upgradeSpyChecker;
  private final ActionRuleChecker moveSpyChecker;
  private final ActionRuleChecker researchCloakingChecker;
  private final ActionRuleChecker cloakingChecker;

  /**
   * Constructs an ActionRuleCheckerHelper, by initializing a ActionRuleChecker for each type of
   * actions via respective rule checker chains.
   */
  public ActionRuleCheckerHelper() {
    this.moveChecker =
        new TerritoryExistenceRuleChecker(
            new SrcOwnershipRuleChecker(
                new TroopExistenceRuleChecker(
                    new EnoughUnitsRuleChecker(
                        new DesReachableRuleChecker(new EnoughResourceRuleChecker(null))))));
    this.attackChecker =
        new TerritoryExistenceRuleChecker(
            new SrcOwnershipRuleChecker(
                new TroopExistenceRuleChecker(
                    new EnoughUnitsRuleChecker(
                        new DesOwnershipRuleChecker(
                            new DesAdjacencyRuleChecker(new EnoughResourceRuleChecker(null)))))));
    this.upgradeUnitChecker =
        new TerritoryExistenceRuleChecker(
            new SrcOwnershipRuleChecker(
                new TroopExistenceRuleChecker(
                    new EnoughUnitsRuleChecker(
                        new UpgradeUnitRuleChecker(new EnoughResourceRuleChecker(null))))));
    this.upgradeTechChecker = new UpgradeTechRuleChecker(new EnoughResourceRuleChecker(null));
    this.moveSpyChecker =
        new TerritoryExistenceRuleChecker(
            new SpyValidityRuleChecker(
                new DesAdjacencyRuleChecker(new EnoughResourceRuleChecker(null))));
    this.upgradeSpyChecker =
        new TerritoryExistenceRuleChecker(
            new SrcOwnershipRuleChecker(
                new TroopExistenceRuleChecker(
                    new EnoughUnitsRuleChecker(new EnoughResourceRuleChecker(null)))));
    this.researchCloakingChecker =
        new ResearchCloakingRuleChecker(new EnoughResourceRuleChecker(null));
    this.cloakingChecker =
        new TerritoryExistenceRuleChecker(
            new SrcOwnershipRuleChecker((new EnoughResourceRuleChecker(null))));
  }

  /**
   * Checks if all the action rules are met to perform a move action on a given worldmap. Calls the
   * checkAction() of moveChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForMove(ActionInfo action, WorldMap worldmap) {
    return this.moveChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an attack action on a given worldmap. Calls
   * the checkAction() of attackChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForAttack(ActionInfo action, WorldMap worldmap) {
    return this.attackChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade unit action on a given worldmap.
   * Calls the checkAction() of upgradeUnitChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForUpgradeUnit(ActionInfo action, WorldMap worldmap) {
    return this.upgradeUnitChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade tech action on a given worldmap.
   * Calls the checkAction() of upgradeTechChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForUpgradeTech(ActionInfo action, WorldMap worldmap) {
    return this.upgradeTechChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade tech action on a given worldmap.
   * Calls the checkAction() of moveSpyChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForMoveSpy(ActionInfo action, WorldMap worldmap) {
    return this.moveSpyChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade tech action on a given worldmap.
   * Calls the checkAction() of upgradeSpyChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForUpgradeSpy(ActionInfo action, WorldMap worldmap) {
    return this.upgradeSpyChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade tech action on a given worldmap.
   * Calls the checkAction() of researchCloakingChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForResearchCloaking(ActionInfo action, WorldMap worldmap) {
    return this.researchCloakingChecker.checkAction(action, worldmap);
  }

  /**
   * Checks if all the action rules are met to perform an upgrade tech action on a given worldmap.
   * Calls the checkAction() of cloakingChecker.
   *
   * @param action is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return error message if violates action rules, else null
   */
  public String checkRuleForCloaking(ActionInfo action, WorldMap worldmap) {
    return this.cloakingChecker.checkAction(action, worldmap);
  }
}
