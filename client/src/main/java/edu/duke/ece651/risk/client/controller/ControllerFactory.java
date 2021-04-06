package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;

import java.util.HashMap;

public class ControllerFactory {
    private static HashMap<String, Object> controllers;

    public Object getController(String controllerName, Object model){
        this.controllers = makeControllers(model);
        return controllers.get(controllerName);
    }

    private static HashMap<String, Object>  makeControllers(Object model) {
        HashMap<String, Object> controllers = new HashMap<>();
        controllers.put("serverConnect", new ServerConnectController(model));
        controllers.put("userLogin", new UserLoginController((App) model));
        controllers.put("joinRoom", new JoinRoomController((App) model));
        controllers.put("selectTerritoryGroup2P", new SelectTerritoryGroup2PController((App) model));
        controllers.put("selectTerritoryGroup3P", new SelectTerritoryGroup3PController((App) model));
        controllers.put("selectTerritoryGroup4P", new SelectTerritoryGroup4PController((App) model));
        controllers.put("selectTerritoryGroup5P", new SelectTerritoryGroup5PController((App) model));
        controllers.put("allocateTalents2p", new AllocateTalents2PController((App) model));
        controllers.put("allocateTalents3p", new AllocateTalents3PController((App) model));
        controllers.put("allocateTalents4p", new AllocateTalents4PController((App) model));
        controllers.put("allocateTalents5p", new AllocateTalents5PController((App) model));
        //Add new controllers here
        controllers.put("test", new ServerConnectController(model));
        return controllers;
    }
}