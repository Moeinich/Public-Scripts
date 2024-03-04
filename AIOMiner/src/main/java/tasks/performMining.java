package tasks;

import helpers.utils.Tile;
import utils.MiningHelper;
import utils.Task;

import static helpers.Interfaces.*;
import static main.AIOMiner.*;

public class performMining extends Task {
    MiningHelper miningHelper = new MiningHelper();
    Tile location;

    public boolean activate() {
        location = Walker.getPlayerPosition(regionInfo.getWorldRegion()); // Cache our position so we only need to check once per loop
        return Player.isTileWithinArea(location, regionInfo.getMineArea());
    }
    @Override
    public boolean execute() {
        if (hopEnabled) {
            hopActions();
        }

        //Move to spot
        if (!Player.tileEquals(locationInfo.getStepLocation(), location)) {
            Logger.log("Stepping to vein spot");
            Walker.step(locationInfo.getStepLocation(), regionInfo.getWorldRegion());
            Condition.wait(() -> Player.atTile(locationInfo.getStepLocation(), regionInfo.getWorldRegion()), 200, 30);
        }

        //perform mining
        if (Player.tileEquals(locationInfo.getStepLocation(), location)) {
            Logger.debugLog("Mining...");
            return doMining();
        }
        return false;
    }

    private boolean doMining() {
        return miningHelper.checkPositionsAndPerformActions(locationInfo, veinColors);
    }

    private void hopActions() {
        Game.hop(hopProfile, useWDH, false);
    }
}
