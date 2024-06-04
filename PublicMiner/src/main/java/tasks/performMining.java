package tasks;

import helpers.utils.Tile;
import utils.MiningHelper;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;

public class performMining extends Task {
    MiningHelper miningHelper = new MiningHelper();
    Tile location;

    public boolean activate() {
        location = Walker.getPlayerPosition(regionInfo.getWorldRegion()); // Cache our position so we only need to check once per loop

        // Check if we should go to mining spot
        if (!Inventory.isFull() && Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Logger.log("Walking to mining spot!");
            Walker.walkPath(regionInfo.getWorldRegion(), miningHelper.pickRandomPathReversed(pathsToBanks));
            Condition.wait(() -> Player.within(regionInfo.getMineArea(), regionInfo.getWorldRegion()), 100, 10);
            return true;
        }

        return Player.isTileWithinArea(location, regionInfo.getMineArea());
    }
    @Override
    public boolean execute() {
        //Move to spot
        if (!Player.tileEquals(locationInfo.getStepLocation(), location)) {
            Logger.log("Stepping to vein spot");
            Walker.step(locationInfo.getStepLocation(), regionInfo.getWorldRegion());
            Condition.wait(() -> Player.atTile(locationInfo.getStepLocation(), regionInfo.getWorldRegion()), 200, 10);
            return true;
        }

        //perform mining
        if (Player.tileEquals(locationInfo.getStepLocation(), location)) {
            Logger.debugLog("Mining...");
            return doMining();
        }

        return false; // Nothing to do
    }

    private boolean doMining() {
        return miningHelper.performMining(locationInfo, veinColors);
    }
}
