package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.Player;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.function.Function;

public class ControllerFactory {
    private static HashMap<String, Object> controllers;
    HashMap<String, Function<Object, Object>> controllerConstructors;

    public ControllerFactory(){
        controllerConstructors = new HashMap<String, Function<Object, Object>>();
        setupControllerConstructors();
    }

    public Object getController(String controllerName, Object model){
        Function<Object, Object> constructor = controllerConstructors.get(controllerName);
        return constructor.apply(model);
    }
    /*
    public Object getController(String controllerName, Object model) {
        this.controllers = makeControllers(model);
        return controllers.get(controllerName);
    }*/

    public Object getActionController(
            String controllerName, Object model, String srcName, String destName, Stage mainPage) {
        this.controllers = makeActionControllers(model, srcName, destName, mainPage);
        return controllers.get(controllerName);
    }

    private void setupControllerConstructors(){
        controllerConstructors.put("serverConnect", (model) -> new ServerConnectController(model));
        controllerConstructors.put("userLogin", (model) -> new UserLoginController((App) model));
        controllerConstructors.put("joinRoom", (model) -> new JoinRoomController((App) model));
        controllerConstructors.put("selectTerritoryGroup", (model) -> new SelectTerritoryGroupController((App) model));
        controllerConstructors.put("allocateTalents", (model) -> new AllocateTalentsController((App) model));
        controllerConstructors.put("selectAction", (model) -> new SelectActionController((App) model));
        controllerConstructors.put("gameEnd", (model) -> new GameEndController((App) model));
        controllerConstructors.put("watchGame", (model) -> new WatchGameController((App) model));
        controllerConstructors.put(
                "loading", (model) -> new LoadingController((App) model, "Waiting for other players..."));
        controllerConstructors.put("characterInfo", (model) -> new CharacterInfoController((App) model));
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
        controllers.put("watchGame", new WatchGameController((App) model));
        controllers.put(
                "loading", new LoadingController((App) model, "Waiting for other players..."));
        controllers.put("characterInfo", new CharacterInfoController((App) model));
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
        controllers.put(
                "upgradeTalentsAction",
                new UpgradeTalentsActionController((App) model, srcName, mainPage));
        controllers.put(
                "upgradeTechAction", new UpgradeTechActionController((App) model, mainPage));
        controllers.put(
                "moveSpyAction",
                new MoveSpyActionController((App) model, srcName, destName, mainPage));
        controllers.put(
                "researchCloakingAction",
                new ResearchCloakingActionController((App) model, mainPage));
        controllers.put(
                "cloakingAction", new CloakingActionController((App) model, srcName, mainPage));
        controllers.put(
                "researchPatentAction", new ResearchPatentActionController((App) model, mainPage));
        return controllers;
    }
}
