package edu.duke.ece651.risk.client.view;

import java.util.HashMap;

public class StyleMapping {
    private static HashMap<String, String> territoryLabels = makeLabels();

    private static HashMap<String, String> makeLabels() {
        HashMap<String, String> labels = new HashMap<>();
        labels.put("Fuqua", "#label0");
        labels.put("Law", "#label1");
        labels.put("Gross Hall", "#label2");
        labels.put("FFRC", "#label3");
        labels.put("Bryan Center", "#label4");
        labels.put("LSRC", "#label5");
        labels.put("Pratt", "#label6");
        labels.put("Perkins Library", "#label7");
        labels.put("Duke Hospital", "#label8");
        labels.put("Duke Clinics", "#label9");
        labels.put("Duke Garden", "#label10");
        labels.put("Duke Chapel", "#label11");
        labels.put("Student Housing", "#label12");
        labels.put("Wilson Gym", "#label13");
        labels.put("Cameron Stadium", "#label14");
        labels.put("Wallace Stadium", "#label15");
        return labels;
    }

}
