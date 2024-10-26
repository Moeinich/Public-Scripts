package tasks;

import helpers.utils.Tile;
import utils.LocationInfo;
import utils.MiningHelper;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;
import static utils.utils.pickRandomPathReversed;

public class performMining extends Task {
    MiningHelper miningHelper = new MiningHelper();
    Tile location;

    @Override
    public boolean activate() {
        location = Walker.getPlayerPosition();
        if (Inventory.isFull()) {
            return false;
        }
        return isAtStepLocation() || handleMiningAreaCheck() || handleBankAreaCheck();
    }

    @Override
    public boolean execute() {
        if (!isAtStepLocation()) {
            return handleMiningAreaCheck();
        }
        return doMining();
    }

    private boolean handleMiningAreaCheck() {
        if (Player.isTileWithinArea(location, regionInfo.getMineArea())) {
            Logger.log("Stepping to mine spot");
            Walker.step(locationInfo.getStepLocation());
            Condition.wait(() -> Player.atTile(locationInfo.getStepLocation()), 100, 20);
            return true;
        } else {
            Walker.webWalk(locationInfo.getStepLocation(), true);
        }
        return false;
    }

    private boolean handleBankAreaCheck() {
        if (Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Logger.log("Walking to mining spot!");
            if (Walker.isReachable(locationInfo.getStepLocation())) {
                Walker.step(locationInfo.getStepLocation());
            } else {
                Walker.webWalk(locationInfo.getStepLocation(), true);
            }
            Condition.wait(() -> Player.within(regionInfo.getMineArea()), 100, 20);
            return true;
        }
        return false;
    }

    private boolean isAtStepLocation() {
        return Player.atTile(locationInfo.getStepLocation());
    }

    private boolean doMining() {
        return miningHelper.performMining(locationInfo, veinColors);
    }
}
