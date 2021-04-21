package edu.duke.ece651.risk.client.view;

import java.util.HashMap;

public class PhaseStorage {
    private static HashMap<String, Phase> phases = makePhases();

    private static HashMap<String, Phase> makePhases() {
        HashMap<String, Phase> phases = new HashMap<>();
        Phase phase1 =
                makePhase(
                        "serverConnect",
                        "Duke Risk Game! - Server Connect",
                        "/ui/views/server-connect.fxml",
                        "/ui/styling/general-design.css");
        Phase phase2 =
                makePhase(
                        "userLogin",
                        "Duke Risk Game! - Log In",
                        "/ui/views/user-login.fxml",
                        "/ui/styling/general-design.css");
        Phase phase3 =
                makePhase(
                        "joinRoom",
                        "Duke Risk Game! - Join Room",
                        "/ui/views/join-room.fxml",
                        "/ui/styling/general-design.css");
        Phase phase4 =
                makePhase(
                        "selectTerritoryGroup",
                        "Duke Risk Game! - Choose Your Character",
                        "/ui/views/select-territory.fxml",
                        "/ui/styling/territory-group.css",
                        "/ui/styling/territory.css");
        Phase phase5 =
                makePhase(
                        "allocateTalents",
                        "Duke Risk Game! - Deploy Talents To Your Territories",
                        "/ui/views/allocate-talents.fxml",
                        "/ui/styling/territory-group.css",
                        "/ui/styling/territory.css",
                        "/ui/styling/general-design.css");
        Phase phase6 =
                makePhase(
                        "selectAction",
                        "Duke Risk Game! - Select Actions",
                        "/ui/views/select-action.fxml",
                        "/ui/styling/territory-group.css",
                        "/ui/styling/territory.css",
                        "/ui/styling/general-design.css",
                        "/ui/styling/action.css");
        Phase phase7 =
                makePhase(
                        "moveAction",
                        "Duke Risk Game! - Move Talents",
                        "/ui/views/move-action.fxml",
                        "/ui/styling/territory-group.css");
        Phase phase8 =
                makePhase(
                        "attackAction",
                        "Duke Risk Game! - Attack Enemy Territory",
                        "/ui/views/attack-action.fxml",
                        "/ui/styling/territory-group.css",
                        "/ui/styling/general-design.css");
        Phase phase9 =
                makePhase(
                        "upgradeTalentsAction",
                        "Duke Risk Game! - Upgrade Your Talents",
                        "/ui/views/upgrade-talents-action.fxml",
                        "/ui/styling/territory-group.css");
        Phase phase10 =
                makePhase(
                        "upgradeTechAction",
                        "Duke Risk Game! - Upgrade Your Tech Level",
                        "/ui/views/upgrade-tech-action.fxml",
                        "/ui/styling/territory-group.css");
        Phase phase11 =
                makePhase(
                        "moveSpyAction",
                        "Duke Risk Game! - Move Your Spy",
                        "/ui/views/move-spy-action.fxml",
                        "/ui/styling/territory-group.css");
        Phase phase12 =
                makePhase(
                        "upgradeSpyAction",
                        "Duke Risk Game! - Upgrade Your Spy",
                        "/ui/views/upgrade-spy-action.fxml",
                        "/ui/styling/territory-group.css");
        Phase phase13 =
                makePhase("gameEnd", "Duke Risk Game! - Game End", "/ui/views/game-end.fxml");
        Phase phase14 = makePhase("loading", "Waiting for Other Players", "/ui/views/loading.fxml");
        Phase phaseTest = makePhase("test", "Duke Risk Game! - Test", "/ui/views/test.fxml");
        // Add new Phases here

        phases.put(phase1.getName(), phase1);
        phases.put(phase2.getName(), phase2);
        phases.put(phase3.getName(), phase3);
        phases.put(phase4.getName(), phase4);
        phases.put(phase5.getName(), phase5);
        phases.put(phase6.getName(), phase6);
        phases.put(phase7.getName(), phase7);
        phases.put(phase8.getName(), phase8);
        phases.put(phase9.getName(), phase9);
        phases.put(phase10.getName(), phase10);
        phases.put(phase11.getName(), phase11);
        phases.put(phase12.getName(), phase12);
        phases.put(phase13.getName(), phase13);
        phases.put(phase14.getName(), phase14);
        phases.put(phaseTest.getName(), phaseTest);
        // Put new Phases into HashMap here
        return phases;
    }

    private static Phase makePhase(
            String name, String windowTitle, String xmlPath, String... cssPaths) {
        View view = new View(name, xmlPath, cssPaths);
        Phase window = new Phase(view, windowTitle);
        return window;
    }

    public static void addPhase(Phase newPhase) {
        phases.put(newPhase.getName(), newPhase);
    }

    public static Phase getPhase(String phaseName) {
        return phases.get(phaseName);
    }
}
