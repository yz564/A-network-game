package edu.duke.ece651.risk.client.view;

import java.util.HashMap;

public class PhaseStorage {
  private static HashMap<String, Phase> phases = makePhases();

  private static HashMap<String, Phase> makePhases() {
    HashMap<String, Phase> phases = new HashMap<>();
    Phase phase1 =
        makePhase(
            "serverConnect",
            "/ui/views/server-connect.fxml",
            "/ui/styling/general-design.css",
            "Duke Risk Game! - Server Connect");
    Phase phase2 =
        makePhase(
            "userLogin",
            "/ui/views/user-login.fxml",
            "/ui/styling/general-design.css",
            "Duke Risk Game! - Log In");
    Phase phase3 =
        makePhase(
            "joinRoom",
            "/ui/views/join-room.fxml",
            "/ui/styling/general-design.css",
            "Duke Risk Game! - Join Room");
    Phase phase4_1 =
        makePhase(
                "selectTerritoryGroupEven",
                "/ui/views/select-territory-even.fxml",
                "/ui/styling/territory-group.css",
                "Duke Risk Game! - Choose Your Character");
    Phase phase4_2 =
        makePhase(
                "selectTerritoryGroupOdd",
                "/ui/views/select-territory-odd.fxml",
                "/ui/styling/territory-group.css",
                "Duke Risk Game! - Choose Your Character");
    Phase phase5_1 =
        makePhase(
            "allocateTalents2p",
            "/ui/views/allocate-talents-2p.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Deploy Talents To Your Territories");
    Phase phase5_2 =
        makePhase(
            "allocateTalents3p",
            "/ui/views/allocate-talents-3p.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Deploy Talents To Your Territories");
    Phase phase5_3 =
        makePhase(
            "allocateTalents4p",
            "/ui/views/allocate-talents-4p.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Deploy Talents To Your Territories");
    Phase phase5_4 =
        makePhase(
            "allocateTalents5p",
            "/ui/views/allocate-talents-5p.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Deploy Talents To Your Territories");
    Phase phase6_1 =
        makePhase(
            "selectActionEvenPlayers",
            "/ui/views/select-action-even.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Select Actions");
    Phase phase6_2 =
        makePhase(
            "selectActionOddPlayers",
            "/ui/views/select-action-odd.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Select Actions");
    Phase phase7_1 =
        makePhase(
            "moveActionEvenPlayers",
            "/ui/views/move-action-even.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Move Talents");
    Phase phase7_2 =
        makePhase(
            "moveActionOddPlayers",
            "/ui/views/move-action-odd.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Move Talents");
    Phase phase8_1 =
        makePhase(
            "attackActionEvenPlayers",
            "/ui/views/attack-action-even.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Attack Enemy Territory");
    Phase phase8_2 =
        makePhase(
            "attackActionOddPlayers",
            "/ui/views/attack-action-odd.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Attack Enemy Territory");
    Phase phase9_1 =
        makePhase(
            "upgradeTalentsActionEvenPlayers",
            "/ui/views/upgrade-talents-action-even.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Upgrade Your Talents");
    Phase phase9_2 =
        makePhase(
            "upgradeTalentsActionOddPlayers",
            "/ui/views/upgrade-talents-action-odd.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Upgrade Your Talents");
    Phase phase10_1 =
        makePhase(
            "upgradeTechActionEvenPlayers",
            "/ui/views/upgrade-tech-action-even.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Upgrade Your Tech Level");
    Phase phase10_2 =
        makePhase(
            "upgradeTechActionOddPlayers",
            "/ui/views/upgrade-tech-action-odd.fxml",
            "/ui/styling/territory-group.css",
            "Duke Risk Game! - Upgrade Your Tech Level");
    Phase phase11 =
        makePhase("gameEnd", "/ui/views/game-end.fxml", null, "Duke Risk Game! - Game End");
    Phase phase12 = makePhase("loadSelectTerritoryGroup2P",
            "/ui/views/load-select-territory-group-2p.fxml",
            null,
            "Waiting for Other Players");
    Phase phaseTest = makePhase("test", "/ui/views/test.fxml", null, "Duke Risk Game! - Test");
    // Add new Phases here

    phases.put(phase1.getName(), phase1);
    phases.put(phase2.getName(), phase2);
    phases.put(phase3.getName(), phase3);
    phases.put(phase4_1.getName(), phase4_1);
    phases.put(phase4_2.getName(), phase4_2);
    phases.put(phase5_1.getName(), phase5_1);
    phases.put(phase5_2.getName(), phase5_2);
    phases.put(phase5_3.getName(), phase5_3);
    phases.put(phase5_4.getName(), phase5_4);
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

  private static Phase makePhase(String name, String xmlPath, String cssPath, String windowTitle) {
    View view = new View(name, xmlPath, cssPath);
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
