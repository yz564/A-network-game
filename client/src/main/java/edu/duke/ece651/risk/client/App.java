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
import java.io.PrintStream;
import java.net.Socket;
import java.util.Iterator;

import edu.duke.ece651.risk.shared.ObjectIO;

/**
 *in and out are objectIOStream
 *tmp stores the most recent ObjectIO read by the client (sent from the server)
 */
public class App {
  ObjectInputStream in;
  ObjectOutputStream out;
  ObjectIO tmp;
  BufferedReader stdIn;
  /**
   * A simple constructor
   */
  public App(ObjectInputStream in, ObjectOutputStream out, ObjectIO tmp) {
    this.in = in;
    this.out = out;
    this.tmp = tmp;
    this.stdIn = new BufferedReader(new InputStreamReader(System.in));
  }
  
  /**
   *first wait to read the ObjectIO sent by the server.
   *then let the user to select the available group
   *finnaly send the ObjectIO with the selection to the server.  
   */
  public void doInitialization() throws Exception {
    String tmpS;
    if ((tmp = (ObjectIO) in.readObject()) != null) {
    }
    while (true) {
      System.out.println(tmp.message);
      System.out.println("Your available choices are: ");
      Iterator<Integer> itr = (tmp.groups).iterator();
      while (itr.hasNext()) {
        Integer g = (Integer) itr.next();
        System.out.println(Integer.toString(g) + " : " + tmp.map.getInitGroup(g));
      }

      if ((tmpS = stdIn.readLine()) != null) {
      }
      try{if (tmp.groups.contains(Integer.parseInt(tmpS))) {
        break;
        }}catch(NumberFormatException e){
        System.out.println("Input should be a number, please retry");
      }
      System.out.println("Your input is not valid, please retry");
    }
    out.writeObject(new ObjectIO(tmpS));
    out.flush();
    out.reset();
  }
  /**
   *first wait the ObjectIO from server, then call the placeOrder method in the helper class, finally send ObjectIO to server.
   */
public void doPlacement() throws Exception {
  System.out.println("-----waitServerInput-----");
      if ((tmp = (ObjectIO) in.readObject()) != null) {
        String playerName = tmp.message;
        ClientOrderHelper coh = new ClientOrderHelper(playerName, stdIn, new PrintStream(System.out));
        out.writeObject(coh.issuePlaceOrders(tmp.id, tmp.playerNames)); //here tmp.playerNames is territory names...
      }
    
  }
  
  /**
   *first wait the ObjectIO from server, then call the placeOrder method in the helper class, finally send ObjectIO to server
   *need to check the status of the player: win or lose
   */
  public void doAction() throws Exception {
    while (true) {
      System.out.println("-----waitServerInput-----");
      if ((tmp = (ObjectIO) in.readObject()) != null) {
        if (tmp.id < 0) {
          break;
        }
        MapTextView mapview = new MapTextView(tmp.playerNames);
        System.out.println(mapview.displayMap(tmp.map));
        System.out.println(tmp.message);
        String playerName = tmp.message;
        ClientOrderHelper coh = new ClientOrderHelper(playerName, stdIn, new PrintStream(System.out));
        out.writeObject(coh.issueActionOrders(tmp.map, tmp.playerNames));

      }
    }
    if (tmp.id == -1) {
      System.out.println("Your lost all territories...");
    }
    if (tmp.id == -2) {
      System.out.println(tmp.message);
    }
    if (tmp.id == -3) {
      System.out.println("You win!");
    }
  }
  
  /**
   *each turn ask the user watch or not, enter something start with /q will quit, no longer print the game
   *enter others will update the game's map  
   */
  public void doWatch() throws Exception {
    while (true) {
      if ((tmp = (ObjectIO) in.readObject()) != null) {
        MapTextView mapview = new MapTextView(tmp.playerNames);
        System.out.println(mapview.displayMap(tmp.map));
        System.out.println(tmp.message);
      }
      String tmpstr;
      System.out.println("Do you want watch? you can quit by /q");
      if ((tmpstr = stdIn.readLine()) != null) {
        if (tmpstr.toLowerCase().startsWith("/q")) {
          System.out.println("quited");
          break;
        }
      }
    }
  }

/**
 *the enter point of the client.
 *after connecting with the server, new App, and call its method to communicate with the server(game).
 */
  public static void main(String[] args) throws Exception {
    System.out.println("Please enter server address: (default is localhost by hitting Enter)");
    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
    String ServerAddress = stdIn.readLine();
    if (ServerAddress.equals("")) {
      ServerAddress = "localhost";
    }
    System.out.println("Please enter server port number: (default is 3333 by hitting Enter)");
    String tmpS = stdIn.readLine();
    int portNumber = 0;
    if (tmpS.equals("")) {
      portNumber = 3333;
    } else {
      portNumber = Integer.parseInt(tmpS);
    }
    try (var server = new Socket(ServerAddress, portNumber)) {
      ObjectOutputStream out = new ObjectOutputStream(server.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(server.getInputStream());
      ObjectIO tmp = null;
      App client = new App(in, out, tmp);
      System.out.println("wait other players...");
      client.doInitialization();
      client.doPlacement();
      System.out.println("Initialization is done");
      client.doAction();
      client.doWatch();
      //System.exit(0);
      while (true) {
      }
    }catch(Exception e){}
  }
}












