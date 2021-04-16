package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;

import java.util.HashMap;

public class ControllerFactory {
  private static HashMap<String, Object> controllers;

  public Object getController(String controllerName, Object model) {
    this.controllers = makeControllers(model);
    return controllers.get(controllerName);
  }

  private static HashMap<String, Object> makeControllers(Object model) {
    HashMap<String, Object> controllers = new HashMap<>();
    controllers.put("serverConnect", new ServerConnectController(model));
    controllers.put("userLogin", new UserLoginController((App) model));
    controllers.put("joinRoom", new JoinRoomController((App) model));
    controllers.put("selectTerritoryGroup", new SelectTerritoryGroupController((App) model));
    controllers.put("allocateTalents", new AllocateTalentsController((App) model));
    controllers.put("selectAction", new SelectActionController((App) model));
    controllers.put("moveAction", new MoveActionController((App) model));
    controllers.put("attackAction", new AttackActionController((App) model));
    controllers.put(
        "upgradeTalentsAction", new UpgradeTalentsActionController((App) model));
    controllers.put("upgradeTechAction", new UpgradeTechActionsController((App) model));
    controllers.put("moveSpyAction", new MoveSpyActionController((App) model));
    controllers.put("upgradeSpyAction", new UpgradeSpyActionController((App) model));
    controllers.put("gameEnd", new GameEndController((App) model));

    // Add new controllers here
    controllers.put("test", new ServerConnectController(model));
    return controllers;
  }
}
