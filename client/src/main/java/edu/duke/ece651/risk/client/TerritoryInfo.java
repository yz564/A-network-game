package edu.duke.ece651.risk.client;

import edu.duke.ece651.risk.shared.Troop;

import java.util.HashMap;

public class TerritoryInfo {
    final String territoryName;
    final String domain;
    String ownerName;
    HashMap<String, Integer> troopNum;
    int playerSpyNum;
    int cloakingTurns;
}
