package edu.duke.ece651.risk.shared;

public class LevelTroop implements Troop{

    @Override
    public boolean tryAddUnits(int toAdd) {
        return false;
    }

    @Override
    public boolean tryRemoveUnits(int toRemove) {
        return false;
    }

    @Override
    public int getNumUnits() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean trySetNumUnits(int numUnits) {
        return false;
    }

    @Override
    public int getBonus() {
        return 0;
    }
}
