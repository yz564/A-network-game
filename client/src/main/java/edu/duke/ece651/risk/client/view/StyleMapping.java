package edu.duke.ece651.risk.client.view;

import java.util.HashMap;

public class StyleMapping {
    private static HashMap<String, String> territoryLabels = makeLabels();
    private static HashMap<Integer, String> territoryGroupColors = makeColors();

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

    public String getTerritoryLabelId(String territoryName){
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

    public String territoryGroupColor(int group){
        return territoryGroupColors.get(group);
    }
}
