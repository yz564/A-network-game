package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import javafx.stage.Stage;

import java.util.HashMap;

public class ControllerFactory {
    private static HashMap<String, Object> controllers;

    public Object getController(String controllerName, Object model) {
        this.controllers = makeControllers(model);
        return controllers.get(controllerName);
    }

    public Object getActionController(
            String controllerName, Object model, String srcName, String destName, Stage mainPage) {
        this.controllers = makeActionControllers(model, srcName, destName, mainPage);
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
        controllers.put("gameEnd", new GameEndController((App) model));
        controllers.put(
                "loading",
                new LoadingController((App) model, "Waiting for other players to join..."));
        // Add new controllers here
        controllers.put("test", new ServerConnectController(model));
        return controllers;
    }

    private static HashMap<String, Object> makeActionControllers(
            Object model, String srcName, String destName, Stage mainPage) {
        HashMap<String, Object> controllers = new HashMap<>();
        controllers.put(
                "moveAction", new MoveActionController((App) model, srcName, destName, mainPage));
        controllers.put(
                "attackAction",
                new AttackActionController((App) model, srcName, destName, mainPage));
        controllers.put("upgradeTalentsAction", new UpgradeTalentsActionController((App) model));
        controllers.put("upgradeTechAction", new UpgradeTechActionController((App) model, mainPage));
        controllers.put("moveSpyAction", new MoveSpyActionController((App) model));
        controllers.put("upgradeSpyAction", new UpgradeSpyActionController((App) model));
        controllers.put("researchCloakingAction", new ResearchCloakingActionController((App) model, mainPage));
        controllers.put("cloakingAction", new CloakingActionController((App) model, srcName, mainPage));
        return controllers;
    }
}
