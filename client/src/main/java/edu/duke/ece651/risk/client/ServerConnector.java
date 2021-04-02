package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.ObjectIO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnector {
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ObjectIO comPackage;
    private App client;

    public ServerConnector() {
        this.socket = null;
        this.in = null;
        this.out = null;
        this.comPackage = null;
        this.client = null;
    }

  public String tryConnect(String serverAdd) {
      App client=new App(serverAdd);
      Thread t=new Thread(client);
      t.start();
      /*
      client.start();
        try (var socket = new Socket(serverAdd, 3333)){
            this.socket = socket;
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
            this.comPackage = null;
            this.client = new App(socket, in, out, comPackage);
            client.start();
        }
        catch (Exception e){
            return "Server address does not exist!";
        }
      */
        return null;

    }

}











