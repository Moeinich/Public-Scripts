package tasks;

import helpers.utils.Tile;
import utils.MiningHelper;
import utils.Task;

import static helpers.Interfaces.*;
import static main.AIOMiner.*;

public class Bank extends Task {
    MiningHelper miningHelper = new MiningHelper();
    String dynamicBank;
    Tile location;

    public boolean activate() {
        // Early exit if banking is disabled!
        if (!bankOres) {
            return false;
        }
        Logger.debugLog("Checking if we should bank");
        location = Walker.getPlayerPosition(regionInfo.getWorldRegion());
        if (!Inventory.isFull() && Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Walker.walkPath(regionInfo.getWorldRegion(), miningHelper.pickRandomPathReversed(pathsToBanks));
        }

        return Inventory.isFull();
    }
    @Override
    public boolean execute() {
        Logger.log("Banking ores!");
        if (!Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Logger.log("Not at the bank, walking there");
            Walker.walkPath(regionInfo.getWorldRegion(), miningHelper.pickRandomPath(pathsToBanks));
        }

        if (Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            if (dynamicBank == null) {
                Logger.log("Finding bank");
                dynamicBank = Bank.setupDynamicBank();
            } else {
                Bank.stepToBank();
            }

            if (!Bank.isOpen()) {
                Bank.open(dynamicBank);
                Condition.wait(() -> Bank.isOpen(), 100, 10);
            }

            if (Bank.isOpen()) {
                if (!Bank.isSelectedQuantityAllButton()) {
                    Client.tap(Bank.findQuantityAllButton());
                }

                if (Bank.isSelectedQuantityAllButton()) {
                    Inventory.tapItem(oreTypeInt, 0.98);
                    Condition.wait(() -> !Inventory.isFull(), 100, 10);
                    Bank.close();
                }
            }
        }

        return false;
    }
}
