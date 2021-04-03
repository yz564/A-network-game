package edu.duke.ece651.risk.client;

import java.util.HashMap;

public class Phase {
    private HashMap<String, View> views;
    private HashMap<String, String> windowTitles;


    public View getView(String name){
        return views.get(name);
    }
}
