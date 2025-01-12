package tasks;

import helpers.utils.ItemList;
import helpers.utils.Tile;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;
import static utils.utils.pickRandomPath;

public class Bank extends Task {
    String dynamicBank = null;
    Tile location;

    public boolean activate() {
        // Early exit if banking is disabled
        if (!bankOres) {
            return false;
        }

        return Inventory.isFull();
    }

    @Override
    public boolean execute() {
        location = Walker.getPlayerPosition();

        if (!Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Logger.log("Not at the bank, walking there");
            Walker.walkPath(pickRandomPath(pathsToBanks));
            Player.waitTillNotMoving(10);
        }

        handleBanking();
        return false;
    }

    private void handleBanking() {
        ensureBankIsOpenAndReady();

        if (Bank.isOpen()) {
            Logger.log("Banking ores!");
            bankItems();
        }
    }

    private void ensureBankIsOpenAndReady() {
        stepToBank();
        if (!Bank.isOpen()) {
            Logger.debugLog("Opening bank");
            Bank.open(dynamicBank);
            Condition.wait(Bank::isOpen, 50, 10);
        }
    }

    private void stepToBank() {
        if (dynamicBank == null) {
            Logger.log("Finding bank");
            dynamicBank = Bank.findDynamicBank();
            Logger.debugLog("Reached bank tile.");
        } else {
            Logger.log("Stepping to bank");
            Bank.stepToBank(dynamicBank);
            Logger.debugLog("Reached bank tile");
        }
    }

    private void bankItems() {
        if (!Bank.isSelectedQuantityAllButton()) {
            Client.tap(Bank.findQuantityAllButton());
            Condition.wait(Bank::isSelectedQuantityAllButton, 100, 20);
        }

        Inventory.tapItem(oreTypeInt, 0.60);
        tapAllGemsAndClues();

        Condition.wait(() -> !Inventory.isFull(), 100, 10);
        Bank.close();
    }

    private void tapAllGemsAndClues() {
        tapAllItems(new int[]{
                // Gems
                ItemList.UNCUT_OPAL_1625,
                ItemList.UNCUT_JADE_1627,
                ItemList.UNCUT_RED_TOPAZ_1629,
                ItemList.UNCUT_SAPPHIRE_1623,
                ItemList.UNCUT_EMERALD_1621,
                ItemList.UNCUT_RUBY_1619,
                ItemList.UNCUT_DIAMOND_1617,
                // Clues
                ItemList.CLUE_GEODE_BEGINNER_23442,
                ItemList.CLUE_GEODE_EASY_20358,
                ItemList.CLUE_GEODE_MEDIUM_20360,
                ItemList.CLUE_GEODE_HARD_20362,
                ItemList.CLUE_GEODE_ELITE_20364});
    }

    private void tapAllItems(int[] itemIds) {
        for (int itemId : itemIds) {
            Inventory.tapItem(itemId, false, 0.60);
        }
    }
}
