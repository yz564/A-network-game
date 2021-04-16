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
    Phase phase6_1 =
        makePhase(
            "selectActionEvenPlayers",
            "Duke Risk Game! - Select Actions",
            "/ui/views/select-action-even.fxml",
            "/ui/styling/territory-group.css");
    Phase phase6_2 =
        makePhase(
            "selectActionOddPlayers",
            "Duke Risk Game! - Select Actions",
            "/ui/views/select-action-odd.fxml",
            "/ui/styling/territory-group.css");
    Phase phase7_1 =
        makePhase(
            "moveActionEvenPlayers",
            "Duke Risk Game! - Move Talents",
            "/ui/views/move-action-even.fxml",
            "/ui/styling/territory-group.css");
    Phase phase7_2 =
        makePhase(
            "moveActionOddPlayers",
            "/ui/views/move-action-odd.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Move Talents");
    Phase phase8_1 =
        makePhase(
            "attackActionEvenPlayers",
            "Duke Risk Game! - Attack Enemy Territory",
            "/ui/views/attack-action-even.fxml",
            "/ui/styling/territory-group.css");
    Phase phase8_2 =
        makePhase(
            "attackActionOddPlayers",
            "Duke Risk Game! - Attack Enemy Territory",
            "/ui/views/attack-action-odd.fxml",
            "/ui/styling/territory-group.css");
    Phase phase9_1 =
        makePhase(
            "upgradeTalentsActionEvenPlayers",
            "Duke Risk Game! - Upgrade Your Talents",
            "/ui/views/upgrade-talents-action-even.fxml",
            "/ui/styling/territory-group.css");
    Phase phase9_2 =
        makePhase(
            "upgradeTalentsActionOddPlayers",
            "Duke Risk Game! - Upgrade Your Talents",
            "/ui/views/upgrade-talents-action-odd.fxml",
            "/ui/styling/territory-group.css");
    Phase phase10_1 =
        makePhase(
            "upgradeTechActionEvenPlayers",
            "Duke Risk Game! - Upgrade Your Tech Level",
            "/ui/views/upgrade-tech-action-even.fxml",
            "/ui/styling/territory-group.css");
    Phase phase10_2 =
        makePhase(
            "upgradeTechActionOddPlayers",
            "Duke Risk Game! - Upgrade Your Tech Level",
            "/ui/views/upgrade-tech-action-odd.fxml",
            "/ui/styling/territory-group.css");
    Phase phase11 =
        makePhase("gameEnd", "Duke Risk Game! - Game End", "/ui/views/game-end.fxml", null);
    Phase phase12 = makePhase("loadSelectTerritoryGroup2P",
            "Waiting for Other Players",
            "/ui/views/load-select-territory-group-2p.fxml",
            null);
    Phase phaseTest = makePhase("test", "Duke Risk Game! - Test", "/ui/views/test.fxml", null);
    // Add new Phases here

    phases.put(phase1.getName(), phase1);
    phases.put(phase2.getName(), phase2);
    phases.put(phase3.getName(), phase3);
    phases.put(phase4.getName(), phase4);
    phases.put(phase5.getName(), phase5);
    phases.put(phase6_1.getName(), phase6_1);
    phases.put(phase6_2.getName(), phase6_2);
    phases.put(phase7_1.getName(), phase7_1);
    phases.put(phase7_2.getName(), phase7_2);
    phases.put(phase8_1.getName(), phase8_1);
    phases.put(phase8_2.getName(), phase8_2);
    phases.put(phase9_1.getName(), phase9_1);
    phases.put(phase9_2.getName(), phase9_2);
    phases.put(phase10_1.getName(), phase10_1);
    phases.put(phase10_2.getName(), phase10_2);
    phases.put(phase11.getName(), phase11);
    phases.put(phase12.getName(), phase12);
    phases.put(phaseTest.getName(), phaseTest);
    // Put new Phases into HashMap here
    return phases;
  }

  private static Phase makePhase(String name, String windowTitle, String xmlPath, String... cssPaths) {
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
