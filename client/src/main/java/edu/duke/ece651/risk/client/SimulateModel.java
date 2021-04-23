package edu.duke.ece651.risk.client;

import java.util.HashSet;

/**
 * Brings the player progress until a specific point in the game by simulating a model ath the backend.
 * This class is used for testing controllers of the GUI.
 *
 * Why this class?
 *      Each controller is called at a particular time in the game. Therefore each controller receives model
 *      at a particular point in a gameplay. It has logged in, chosen a character, etc. Testing code requires the model
 *      to be received in the same way way but since there is no player to interact with GUI in testing code, therefore
 *      the requirement of this class.
 */
public class SimulateModel {
    private static GUIEventMessenger messenger = new GUIEventMessenger();

    public static App simulate(String task, String address, String user, String pass, int room) throws Exception {
        App model = new App();
        String serverAddress = address;
        String username = user;
        String password = pass;
        Integer roomId = room;
        switch(task) {
            case "connectServer":
                model = doServerConnect(model, serverAddress);
                break;
            case "userLogin":
                model = doServerConnect(model, serverAddress);
                model = doLogin(model, username, password);
                break;
            case "joinRoom":
                model = doServerConnect(model, serverAddress);
                model = doLogin(model, username, password);
                model = joinRoom(model, roomId);
                break;
            default:
                break;
        }
        return model;
    }

    /**
     * Connects a model to the server with .
     * @param model that is not connected to the server.
     * @param serverAddress is the server address.
     * @return model that is connected to the server.
     * @throws IllegalStateException if server is not able to connect to server (chances are that it is not in correct
     * state).
     */
    private static App doServerConnect(App model, String serverAddress) throws IllegalStateException {
        String connectionError = model.tryConnect(serverAddress);
        if (connectionError != null) {
            throw new IllegalStateException(connectionError);
        }
        return model;
    }

    private static App doLogin(App model, String username, String password) throws Exception {
        Boolean loginSuccess = model.tryLogin(username, password);
        if (!loginSuccess){
            throw new IllegalStateException("Login failed using username='" +
                    username +
                    "' password='" +
                    password + "'");
        }
        return model;
    }

    private static App joinRoom (App model, Integer roomId) {
        Boolean joinRoomSuccess = model.checkIn();
        if (!joinRoomSuccess) {
            throw new IllegalStateException("Unable to join room " + roomId);
        }
        return model;
    }
}
