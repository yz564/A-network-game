package edu.duke.ece651.risk.shared;

public class LevelTroop extends AbstractTroop {

    /**
     * Required tech level for using this troop.
     */
    private final int techLevelReq;

    /**
     * The attack bonus amount the units in this troop have.
     */
    private final int bonus;

    /*
     * Constructs a LevelTroop object.
     *
     * @param name is the name assigned to the troop.
     *
     * @param numUnits is the number of units put in the troop.
     *
     * @param unitLimit is the maximum amount of units that a troop can contain.
     *
     * @param bonus is the bonus for units in the Troop.
     */
    public LevelTroop(String name, int numUnits, int unitLimit, int bonus, int techLevelReq) {
        super(name, numUnits, unitLimit);
        this.techLevelReq = techLevelReq;
        this.bonus = bonus;
    }

    /*
     * Constructs a LevelTroop object with unitLimit assigned with 99999.
     *
     * @param name is the name assigned to the troop.
     *
     * @param numUnits is the number of units put in the troop.
     *
     * @param bonus is the bonus for units in the Troop.
     */
    public LevelTroop(String name, int numUnits, int bonus, int techLevelReq) {
        this(name, numUnits, 99999, bonus, techLevelReq);
    }

    @Override
    public int getTechLevelReq() {
        return techLevelReq;
    }

    @Override
    public int getBonus() {
        return bonus;
    }
}
