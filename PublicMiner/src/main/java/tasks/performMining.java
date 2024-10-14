package tasks;

import helpers.utils.Tile;
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
            return moveTo(locationInfo.getStepLocation());
        }
        return doMining();
    }

    private boolean moveTo(Tile targetLocation) {
        Walker.step(targetLocation);
        Condition.wait(() -> Player.atTile(targetLocation, regionInfo.getWorldRegion()), 100, 20);
        return true;
    }

    private boolean handleMiningAreaCheck() {
        if (Player.isTileWithinArea(location, regionInfo.getMineArea())) {
            Logger.log("Stepping to mine spot");
            Walker.step(locationInfo.getStepLocation());
            Condition.wait(() -> Player.atTile(locationInfo.getStepLocation(), regionInfo.getWorldRegion()), 100, 20);
            return true;
        }
        return false;
    }

    private boolean handleBankAreaCheck() {
        if (Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Logger.log("Walking to mining spot!");
            Walker.walkPath(pickRandomPathReversed(pathsToBanks));
            Condition.wait(() -> Player.within(regionInfo.getMineArea(), regionInfo.getWorldRegion()), 100, 20);
            return true;
        }
        return false;
    }

    private boolean isAtStepLocation() {
        return Player.atTile(locationInfo.getStepLocation(), regionInfo.getWorldRegion());
    }

    private boolean doMining() {
        return miningHelper.performMining(locationInfo, veinColors);
    }
}
