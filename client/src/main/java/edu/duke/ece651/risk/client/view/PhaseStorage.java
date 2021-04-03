package edu.duke.ece651.risk.client.view;

import java.util.HashMap;

public class PhaseStorage {
    private static HashMap<String, Phase> phases = makePhases();

    private static HashMap<String, Phase> makePhases(){
        HashMap<String, Phase> phases = new HashMap<>();
        Phase phase1 = makePhase("serverConnect", "/ui/views/server-connect.fxml", null, "Duke Risk Game! - Server Connect");
        Phase phase2 = makePhase("userLogin", "/ui/views/user-login.fxml", null, "Duke Risk Game! - Log In");
        Phase phase3 = makePhase("joinRoom", "/ui/views/join-room.fxml", null, "Duke Risk Game! - Join Room");
        Phase phaseTest = makePhase("test", "/ui/views/test.fxml", null, "Duke Risk Game! - Test");
        // Add new Phases here
        phases.put(phase1.getName(), phase1);
        phases.put(phase2.getName(), phase2);
        phases.put(phase3.getName(), phase3);
        phases.put(phaseTest.getName(), phaseTest);
        //Put new Phases into HashMap here
        return phases;
    }

    private static Phase makePhase(String name, String xmlPath, String cssPath, String windowTitle) {
        View view = new View(name, xmlPath, cssPath);
        Phase window = new Phase(view, windowTitle);
        return window;
    }

    public static void addPhase(Phase newPhase){
        phases.put(newPhase.getName(), newPhase);
    }

    public static Phase getPhase(String phaseName){
        return phases.get(phaseName);
    }
}
