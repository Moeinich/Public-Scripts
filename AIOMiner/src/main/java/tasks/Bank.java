package tasks;

import helpers.utils.ItemList;
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
        // Early exit if banking is disabled
        if (!bankOres) {
            return false;
        }

        Logger.debugLog("Checking if we should bank");
        location = Walker.getPlayerPosition(regionInfo.getWorldRegion()); // Cache our location

        // Check if we already banked and if so, move back to mining spot
        if (!Inventory.isFull() && Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Walker.walkPath(regionInfo.getWorldRegion(), miningHelper.pickRandomPathReversed(pathsToBanks));
        }

        return Inventory.isFull();
    }
    @Override
    public boolean execute() {
        if (dropClues && dropCluesIfNeeded()) {
            return true;
        }

        // Else handle the banking
        handleBanking();

        return false;
    }

    private boolean dropCluesIfNeeded() {
        if (Inventory.containsAny(clueIDs, 0.60)) {
            Logger.log("Dropping clues");
            tapAllClues();
            Condition.wait(() -> !Inventory.containsAny(clueIDs, 0.60), 100, 20);
            return true;
        }
        return false;
    }

    private void tapAllClues() {
        Inventory.tapItem(ItemList.CLUE_GEODE_BEGINNER_23442, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_EASY_20358, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_MEDIUM_20360, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_HARD_20362, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_ELITE_20364, false, 0.60);
    }

    private void handleBanking() {
        Logger.log("Banking ores!");
        if (!Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Logger.log("Not at the bank, walking there");
            Walker.walkPath(regionInfo.getWorldRegion(), miningHelper.pickRandomPath(pathsToBanks));
        }

        if (Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            ensureBankIsReady();
            if (Bank.isOpen()) {
                bankItems();
            }
        }
    }

    private void ensureBankIsReady() {
        if (dynamicBank == null) {
            Logger.log("Finding bank");
            dynamicBank = Bank.setupDynamicBank();
        } else {
            Bank.stepToBank();
        }

        if (!Bank.isOpen()) {
            Bank.open(dynamicBank);
            Condition.wait(Bank::isOpen, 100, 10);
        }
    }

    private void bankItems() {
        if (!Bank.isSelectedQuantityAllButton()) {
            Client.tap(Bank.findQuantityAllButton());
            Condition.wait(Bank::isSelectedQuantityAllButton, 100, 20);
        }

        Inventory.tapItem(oreTypeInt, 0.60);

        if (Inventory.containsAny(clueIDs, 0.60)) {
            tapAllClues();
        }

        Condition.wait(() -> !Inventory.isFull(), 100, 10);
        Bank.close();
    }
}
