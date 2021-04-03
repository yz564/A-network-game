package edu.duke.ece651.risk.client.controller;

import edu.duke.ece651.risk.client.App;
import edu.duke.ece651.risk.client.view.Phase;

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
        //Add new controllers here
        controllers.put("test", new ServerConnectController(model));
        return controllers;
    }
}