package edu.duke.ece651.risk.client.view;

import java.util.ArrayList;
import java.util.HashMap;

public class StyleMapping {
  private static HashMap<String, String> territoryLabels = makeLabels();
  private static HashMap<Integer, String> territoryGroupColors = makeColors();
  private static HashMap<String, String> unitNameToTalent = makeTalents();
  private static ArrayList<String> TalentNames = makeTalentNames();
  private static ArrayList<String> characterNames = makeCharacterNames();

  private static HashMap<String, String> makeLabels() {
    HashMap<String, String> labels = new HashMap<>();
    labels.put("label0", "Fuqua");
    labels.put("label1", "Law");
    labels.put("label2", "Gross Hall");
    labels.put("label3", "FFRC");
    labels.put("label4", "Bryan Center");
    labels.put("label5", "LSRC");
    labels.put("label6", "Pratt");
    labels.put("label7", "Perkins Library");
    labels.put("label8", "Duke Hospital");
    labels.put("label9", "Duke Clinics");
    labels.put("label10", "Duke Garden");
    labels.put("label11", "Duke Chapel");
    labels.put("label12", "Student Housing");
    labels.put("label13", "Wilson Gym");
    labels.put("label14", "Cameron Stadium");
    labels.put("label15", "Wallace Stadium");
    return labels;
  }

  public String getTerritoryLabelId(String territoryName) {
    return territoryLabels.get(territoryName);
  }

  private static HashMap<Integer, String> makeColors() {
    HashMap<Integer, String> colors = new HashMap<>();
    colors.put(1, "Pink");
    colors.put(2, "Blue");
    colors.put(3, "Orange");
    colors.put(4, "Green");
    colors.put(5, "Purple");
    return colors;
  }

  public String territoryGroupColor(int group) {
    return territoryGroupColors.get(group);
  }

  private static ArrayList<String> makeTalentNames() {
    ArrayList<String> talentNames = new ArrayList<>();
    talentNames.add("Undergrad");
    talentNames.add("Masters");
    talentNames.add("PhD");
    talentNames.add("Postdoc");
    talentNames.add("Asst. Prof");
    talentNames.add("Assc. Prof");
    talentNames.add("Professor");
    return talentNames;
  }

  public ArrayList<String> getTalentNames() {
    return TalentNames;
  }

  private static HashMap<String, String> makeTalents() {
    HashMap<String, String> talents = new HashMap<>();
    talents.put("Undergrad", "level0");
    talents.put("Masters", "level1");
    talents.put("PhD", "level2");
    talents.put("Postdoc", "level3");
    talents.put("Asst. Prof", "level4");
    talents.put("Assc. Prof", "level5");
    talents.put("Professor", "level6");
    return talents;
  }

  public String getTalents(String unitName) {
    return unitNameToTalent.get(unitName);
  }

  private static ArrayList<String> makeCharacterNames(){
    ArrayList<String> characterNames = new ArrayList<>();
    characterNames.add("Melinda Gates"); //Fuqua
    characterNames.add("William Kaelin"); //Medicine
    characterNames.add("Coach K"); //Sports
    characterNames.add("Drew Hilton"); //Pratt
    characterNames.add("Valerie Ashby"); //Trinity
    return characterNames;
  }

  public String getCharacterNames(int id) {
    return characterNames.get(id);
  }
}
