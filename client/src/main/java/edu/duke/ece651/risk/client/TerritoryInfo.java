package edu.duke.ece651.risk.client;

import java.util.HashMap;

public class TerritoryInfo {
    final String playerName;
    final String territoryName;
    final String domain;
    final HashMap<String, Integer> resProduction;
    String ownerName;
    HashMap<String, Integer> troopNum;
    int playerSpyNum;
    int cloakingTurns;

    public TerritoryInfo(
            String playerName,
            String territoryName,
            String domain,
            HashMap<String, Integer> resProduction) {
        this.playerName = playerName;
        this.territoryName = territoryName;
        this.domain = domain;
        this.resProduction = resProduction;
        this.ownerName = "Unknown";
        this.cloakingTurns = 0;
        this.playerSpyNum = 0;
        this.troopNum = new HashMap<>();
        troopNum.put("level0", 0);
        troopNum.put("level1", 0);
        troopNum.put("level2", 0);
        troopNum.put("level3", 0);
        troopNum.put("level4", 0);
        troopNum.put("level5", 0);
        troopNum.put("level6", 0);
    }

    public TerritoryInfo(
            String playerName,
            String territoryName,
            String domain,
            Integer foodProduction,
            Integer techProduction) {
        this(playerName, territoryName, domain, new HashMap<>());
        resProduction.put("food", foodProduction);
        resProduction.put("tech", techProduction);
    }

    public String getTerritoryName() {
        return territoryName;
    }

    public String getDomain() {
        String ans = domain.substring(0, 1).toUpperCase() + domain.substring(1).toLowerCase();
        return ans;
    }

    public HashMap<String, Integer> getResProduction() {
        return resProduction;
    }

    public Integer getFoodProduction() {
        return resProduction.get("food");
    }

    public Integer getTechProduction() {
        return resProduction.get("tech");
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public HashMap<String, Integer> getTroopNum() {
        return troopNum;
    }

    public Integer getOneTroopNum(String oneTroopName) {
        return troopNum.get(oneTroopName);
    }

    public int getTotalTroopNum(){
        int totalNum = 0;
        for (String troopName: troopNum.keySet()){
            totalNum += troopNum.get(troopName);
        }
        return totalNum;
    }

    public void setTroopNum(HashMap<String, Integer> troopNum) {
        this.troopNum = troopNum;
    }

    public Boolean trySetOneTroopNum(String oneTroopName, Integer oneTroopNum) {
        if (this.troopNum.containsKey(oneTroopName)) {
            troopNum.put(oneTroopName, oneTroopNum);
            return true;
        } else {
            return false;
        }
    }

    public int getPlayerSpyNum() {
        return playerSpyNum;
    }

    public void setPlayerSpyNum(int playerSpyNum) {
        this.playerSpyNum = playerSpyNum;
    }

    public int getCloakingTurns() {
        if (playerName.equals(ownerName)) {
            return cloakingTurns;
        } else {
            // cloaking turn is not available to visible enemy.
            return 0;
        }
    }

    public void setCloakingTurns(int cloakingTurns) {
        this.cloakingTurns = cloakingTurns;
    }
}
