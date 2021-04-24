/*
 * This Java source file was generated by the Gradle 'init' task.
 */
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.ece651.risk.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;

import edu.duke.ece651.risk.client.controller.ControllerFactory;
import edu.duke.ece651.risk.shared.ObjectIO;

/**
 * in and out are objectIOStream tmp stores the most recent ObjectIO read by the client (sent from
 * the server)
 */
public class App implements Runnable, GUIEventListener {
    private Socket server;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private ObjectIO tmp;
    private BufferedReader stdIn;
    private ArrayList<Player> players;
    private int currentRoomId;
    private HashSet<Integer> joinedRoomId;
    private String name;
    private GUIEvent theGUIEvent;
    private Boolean isGUIUpdated;
    private ClientEventMessenger messenger;
    private Boolean isLeave;
    private Boolean isRejoin;

    // private String serverAdd;

    public App() {
        this.server = null;
        this.in = null;
        this.out = null;
        this.tmp = null;
        this.stdIn = null;
        this.players = null;
        this.joinedRoomId = null;
        this.name = null;
        this.theGUIEvent = null;
        this.isGUIUpdated = false;
        this.messenger = new ClientEventMessenger();
        this.isLeave = false;
        this.isRejoin = false;
    }

    public void deleteJoinedRoomId(int id) {
        joinedRoomId.remove(id);
    }

    public Player getPlayer() {
        return players.get(currentRoomId);
    }

    public Boolean trySelectTerritory(String info) throws Exception {
        return players.get(currentRoomId).tryInitialization(info);
    }

    public String tryConnect(String serverAdd) {
        try {
            this.server = new Socket(serverAdd, 3333);
            this.out = new ObjectOutputStream(server.getOutputStream());
            this.in = new ObjectInputStream(server.getInputStream());
            this.tmp = null;
            this.initializeApp(server, in, out, tmp);
        } catch (Exception e) {
            return "Server address does not exist!";
        }
        return null;
    }

    public void initializeApp(
            Socket server, ObjectInputStream in, ObjectOutputStream out, ObjectIO tmp) {
        this.server = server;
        this.in = in;
        this.out = out;
        this.tmp = tmp;
        this.stdIn = new BufferedReader(new InputStreamReader(System.in));
        this.players = new ArrayList<Player>();
        for (int i = 0; i < 4; i++) {
            Player p = new Player(i, in, out, stdIn);
            players.add(p);
            Thread t = new Thread(p);
            t.start();
        }
        this.joinedRoomId = new HashSet<Integer>();
    }

    public App(Socket server, ObjectInputStream in, ObjectOutputStream out, ObjectIO tmp) {
        initializeApp(server, in, out, tmp);
    }

    public boolean checkIn() {
        if (!joinedRoomId.contains(
                currentRoomId)) { // if not joined before, new a player and thread
            Player p = players.get(currentRoomId);
            p.setName(name);
            players.set(currentRoomId, p);
            joinedRoomId.add(currentRoomId);
            return true;
        }
        try {
            sendMessage(new ObjectIO("wait"));
            receiveMessage();
        } catch (Exception e) {
        }
        isRejoin = true;
        return false;
    }

    public void requestLeave() throws Exception {
        out.writeObject(new ObjectIO("/leave"));
        out.flush();
        out.reset();
        isLeave = true;
    }

    public ObjectIO receiveMessage() throws Exception {
        try {
            return (ObjectIO) in.readObject();
        } catch (Exception e) {
            throw new Exception("app receiveMessage exception");
        }
    }

    public void sendMessage(ObjectIO info) throws Exception {
        try {
            out.writeObject(info);
            out.flush();
            out.reset();
        } catch (Exception e) {
            throw new Exception("app sendMessage exception");
        }
    }

    public Boolean tryLogin(String userName, String password) throws Exception {
        this.name = userName;
        receiveMessage();
        sendMessage(new ObjectIO(userName));
        receiveMessage();
        sendMessage(new ObjectIO(password));
        tmp = receiveMessage();
        return tmp.id == 0;
    }

    public Boolean tryJoinRoom(int roomId) throws Exception {
        receiveMessage();
        sendMessage(new ObjectIO("", roomId));
        currentRoomId = roomId - 1;
        tmp = receiveMessage();
        return tmp.id == 0;
    }

    public ClientEventMessenger getMessenger() {
        return messenger;
    }

    public void setListener(ClientEventListener listener) {
        messenger.setClientEventListener(listener);
    }

    public void checkGUIUpdate() throws Exception {
        while (!isGUIUpdated) {
            Thread.sleep(10);
        }
        isGUIUpdated = false;
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("run() start from join room");

                checkGUIUpdate();
                receiveMessage();
                int roomId = theGUIEvent.getRoomId();
                currentRoomId = roomId - 1;
                System.out.println("roomId: " + roomId);
                sendMessage(new ObjectIO("", roomId));
                System.out.println("roomId after sending: " + roomId);
                tmp = receiveMessage();
                messenger.setStatusBoolean(tmp.id == 0, "loading");

                if (!isRejoin) {
                    // wait other players to join phase
                    checkGUIUpdate();
                    sendMessage(new ObjectIO("wait others", 0));
                    tmp = receiveMessage();
                    getPlayer().receiveMessage();
                    getPlayer().initializeTerritoriesInfo(); // initialize territoryInfo
                    messenger.setMap(getPlayer().getMap(), "characterInfo");

                    // select territory phase
                    checkGUIUpdate();
                    getPlayer().startAllocation();
                    messenger.setMap(getPlayer().getMap(), "allocateTalents");

                    // allocate talents  phase
                    checkGUIUpdate();
                    getPlayer().receiveMessage();
                    messenger.setMap(getPlayer().getMap(), "selectAction");
                    isRejoin = false;
                }
                // select action phase
                while (true) {
                    while (!isGUIUpdated && !isLeave) {
                        Thread.sleep(10);
                    }
                    if (isLeave) {
                        isLeave = false;
                        break;
                    }
                    isGUIUpdated = false;
                    String result = getPlayer().checkStatus();
                    if (result == null) {
                        messenger.setMap(getPlayer().getMap(), "selectAction");
                    } else if (result.equals("You lose")) {
                        // all territories visible while watching the game
                        for (String territoryName : getPlayer().getMap().getMyTerritories()) {
                            getPlayer()
                                    .getMap()
                                    .getPlayerInfo(name)
                                    .setOneVizStatus(territoryName, true);
                        }
                        getPlayer().updateTerritoryInfo();
                        messenger.setMap(getPlayer().getMap(), "watchGame");
                    } else {
                        messenger.setMap(getPlayer().getMap(), "gameEnd");
                        break;
                    }
                }

                // messenger.setStatusBoolean(true,"selectAction");
            } catch (Exception e) {
                System.out.println("Exception from App run(): " + e.getMessage());
            }
        }
    }

    @Override
    public void onUpdateEvent(GUIEvent ge) {
        this.theGUIEvent = ge;
        this.isGUIUpdated = true;
        System.out.println("isGUIUpdated: " + isGUIUpdated);
        System.out.println("App: onUpdateEvent");
    }
}
