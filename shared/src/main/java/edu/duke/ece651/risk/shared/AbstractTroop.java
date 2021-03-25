package edu.duke.ece651.risk.shared;

public abstract class AbstractTroop implements Troop{
    protected static final long serialVersionUID = -1788807224514071854L;
    protected final String troopName;
    protected final int unitLimit;
    protected int numUnits;
    final protected int bonus;

    /*
     * Construct a AbstractTroop object.
     *
     * @param name is the name assigned to the troop. If name is null then it is set
     * to "BasicTroop".
     *
     * @param numunits is the number of units put in the troop.
     *
     * @param unitLimit is the maximum amount of units that a troop can contain.
     */
    public AbstractTroop(String name, int numUnits, int unitLimit, int bonus) {
       this.troopName = name;
        if (numUnits < 0) {
            throw new IllegalArgumentException("A troop cannot have negative number of units.");
        }
        this.unitLimit = unitLimit;

        if (unitLimit < numUnits) {
            throw new IllegalArgumentException("Number of units in " + this.troopName + " exceed troop limit.");
        }
        this.numUnits = numUnits;
        this.bonus = bonus;
    }

    @Override
    public boolean tryAddUnits(int toAdd) {
        if (toAdd + numUnits > unitLimit) {
            return false;
        }
        numUnits += toAdd;
        return true;
    }

    @Override
    public boolean tryRemoveUnits(int toRemove) {
        if (numUnits - toRemove < 0) {
            return false;
        }
        numUnits -= toRemove;
        return true;
    }

    @Override
    public int getNumUnits() {
        return numUnits;
    }

    @Override
    public String getName() {
        return troopName;
    }

    @Override
    public boolean trySetNumUnits(int numUnits) {
        if (numUnits > this.unitLimit) {
            return false;
        }
        this.numUnits = numUnits;
        return true;
    }

    @Override
    public int getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return this.troopName + " with " + this.numUnits + " units.\n";
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }
}
