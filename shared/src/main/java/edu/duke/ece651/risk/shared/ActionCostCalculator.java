package edu.duke.ece651.risk.shared;

import java.util.HashMap;

public class ActionCostCalculator {
  private final HashMap<Integer, Integer> techLevelCosts;
  private final int attackCost;

  /**
   * Makes the HashMap for tech level costs. To be used in constructor.
   *
   * @return HashMap where key is int of tech level and values is cummalative tech
   *         costs required to upgrage that tech level
   */
  static HashMap<Integer, Integer> makeTechLevelCosts() {
    HashMap<Integer, Integer> techLevelCosts = new HashMap<>();
    techLevelCosts.put(1, 0);
    techLevelCosts.put(2, 50);
    techLevelCosts.put(3, 125);
    techLevelCosts.put(4, 250);
    techLevelCosts.put(5, 450);
    techLevelCosts.put(6, 750);
    return techLevelCosts;
  }

  /**
   * Constructs a ActionCostCalculator. Calls makeTechLevelCosts().
   */
  public ActionCostCalculator() {
    this.techLevelCosts = makeTechLevelCosts();
    this.attackCost = 1;
  }

  /**
   * Calculates the total costs for a move action on a worldmap.
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return HashMap of costs mapped to resource names, null if invalid action
   */
  public HashMap<String, Integer> calculateMoveCost(ActionInfo action, WorldMap worldmap) {
    if (action.getTerritoryActionInfo() != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      Territory src = worldmap.getTerritory(action.getSrcName());
      Territory des = worldmap.getTerritory(action.getDesName());
      int totalNumUnits = action.getTotalNumUnits();
      int minMoveCost = src.findMinMoveCost(des);
      costs.put("food", totalNumUnits * minMoveCost);
      return costs;
    }
    return null;
  }

  public HashMap<String, Integer> calculateMoveSpyCost(ActionInfo action, WorldMap worldmap) {
    if (action.getTerritoryActionInfo() != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      Territory src = worldmap.getTerritory(action.getSrcName());
      Territory des = worldmap.getTerritory(action.getDesName());
      int spyNumUnits = action.getNumSpyUnits();
      int minMoveCost = src.findMinMoveCost(des);
      costs.put("food", spyNumUnits * minMoveCost);
      return costs;
    }
    return null;
  }

  /**
   * Calculates the total costs for an attack action on a worldmap.
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return HashMap of costs mapped to resource names, null if invalid action
   */
  public HashMap<String, Integer> calculateAttackCost(ActionInfo action, WorldMap worldmap) {
    if (action.getTerritoryActionInfo() != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      int totalNumUnits = action.getTotalNumUnits();
      costs.put("food", totalNumUnits * attackCost);
      return costs;
    }
    return null;
  }

  /**
   * Calculates the total costs for an upgrade unit action on a worldmap.
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return HashMap of costs mapped to resource names, null if invalid action
   */
  public HashMap<String, Integer> calculateUpgradeUnitCost(ActionInfo action, WorldMap worldmap) {
    if (action.getUpgradeUnitActionInfo() != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      Territory src = worldmap.getTerritory(action.getSrcName());
      int oldTechCost = src.getTroop(action.getOldUnitLevel()).getTechCost();
      int newTechCost = src.getTroop(action.getNewUnitLevel()).getTechCost();
      int totalNumUnits = action.getTotalNumUnits();
      costs.put("tech", totalNumUnits * (newTechCost - oldTechCost));
      return costs;
    }
    return null;
  }

  public HashMap<String, Integer> calculateUpgradeSpyUnitCost(ActionInfo action, WorldMap worldmap) {
    if (action.getUpgradeUnitActionInfo() != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      int totalNumUnits = action.getTotalNumUnits();
      costs.put("tech", totalNumUnits * 20);
      return costs;
    }
    return null;
  }

  /**
   * Calculates the total costs for an upgrade tech action on a worldmap.
   *
   * @param action   is the ActionInfo for an action
   * @param worldmap is the worldmap to perform the action on
   * @return HashMap of costs mapped to resource names, null if invalid action
   */
  public HashMap<String, Integer> calculateUpgradeTechCost(ActionInfo action, WorldMap worldmap) {
    if (action.getUpgradeTechActionInfo() != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      PlayerInfo playerInfo = worldmap.getPlayerInfo(action.getSrcOwnerName());
      int oldTechCost = techLevelCosts.get(playerInfo.getTechLevel());
      int newTechCost = techLevelCosts.get(action.getNewTechLevel());
      costs.put("tech", newTechCost - oldTechCost);
      return costs;
    }
    return null;
  }
  
  public HashMap<String, Integer> calculateResearchCloakingCost(ActionInfo action, WorldMap worldmap) {
    if (action != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      costs.put("tech", 100);
      return costs;
    }
    return null;
  }
  
  public HashMap<String, Integer> calculateCloakingCost(ActionInfo action, WorldMap worldmap) {
    if (action.getCloakingActionInfo() != null) {
      HashMap<String, Integer> costs = new HashMap<String, Integer>();
      costs.put("tech", 20);
      return costs;
    }
    return null;
  }
  
  public HashMap<String, Integer> calculateCost(ActionInfo action, WorldMap worldmap){
    if (action.getActionType().equals("move")) {
        return calculateMoveCost(action, worldmap);
    }
    if (action.getActionType().equals("attack")) {
      return calculateAttackCost(action, worldmap);
    }
    if (action.getActionType().equals("upgrade unit")) {
      return calculateUpgradeUnitCost(action, worldmap);
    }
    if (action.getActionType().equals("upgrade tech")) {
      return calculateUpgradeTechCost(action, worldmap);
    }
    if (action.getActionType().equals("upgrade spy unit")) {
        return calculateUpgradeSpyUnitCost(action, worldmap);
    }
    if (action.getActionType().equals("move spy")) {
      return calculateMoveSpyCost(action, worldmap);
    }
    if (action.getActionType().equals("research cloaking")) {
      return calculateResearchCloakingCost(action, worldmap);
    }
    if (action.getActionType().equals("cloaking")) {
      return calculateCloakingCost(action, worldmap);
    }
    return null;
  }
}




