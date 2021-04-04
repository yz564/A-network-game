package edu.duke.ece651.risk.client.view;

import java.util.HashMap;

public class PhaseStorage {
    private static HashMap<String, Phase> phases = makePhases();

    private static HashMap<String, Phase> makePhases(){
        HashMap<String, Phase> phases = new HashMap<>();
        Phase phase1 = makePhase("serverConnect", "/ui/views/server-connect.fxml", null, "Duke Risk Game! - Server Connect");
        Phase phase2 = makePhase("userLogin", "/ui/views/user-login.fxml", null, "Duke Risk Game! - Log In");
        Phase phase3 = makePhase("joinRoom", "/ui/views/join-room.fxml", null, "Duke Risk Game! - Join Room");
        Phase phase4_0 = makePhase("selectTerritoryGroup2P", "/ui/views/select-territory-2p.fxml", null, "Duke Risk Game! - Choose Your Territory Group");
        Phase phase4_1 = makePhase("selectTerritoryGroup3P", "/ui/views/select-territory-3p.fxml", null, "Duke Risk Game! - Choose Your Territory Group");
        Phase phase4_3 = makePhase("selectTerritoryGroup4P", "/ui/views/select-territory-4p.fxml", null, "Duke Risk Game! - Choose Your Territory Group");
        Phase phase4_4 = makePhase("selectTerritoryGroup5P", "/ui/views/select-territory-5p.fxml", null, "Duke Risk Game! - Choose Your Territory Group");
        Phase phaseTest = makePhase("test", "/ui/views/test.fxml", null, "Duke Risk Game! - Test");
        // Add new Phases here
        phases.put(phase1.getName(), phase1);
        phases.put(phase2.getName(), phase2);
        phases.put(phase3.getName(), phase3);
        phases.put(phase4_0.getName(), phase4_0);
        phases.put(phase4_1.getName(), phase4_1);
        phases.put(phase4_3.getName(), phase4_3);
        phases.put(phase4_4.getName(), phase4_4);
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
