package tasks;

import helpers.utils.ItemList;
import helpers.utils.Tile;
import utils.MiningHelper;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;

public class Bank extends Task {
    MiningHelper miningHelper = new MiningHelper();
    String dynamicBank;
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
        handleBanking();
        return false;
    }

    private void handleBanking() {
        Logger.log("Banking ores!");
        location = Walker.getPlayerPosition();

        if (!Player.isTileWithinArea(location, regionInfo.getBankArea())) {
            Logger.log("Not at the bank, walking there");
            Walker.walkPath(regionInfo.getWorldRegion(), miningHelper.pickRandomPath(pathsToBanks));
            return;
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
        if(Inventory.containsAny(gemIDs, 0.60)) {
            tapAllGems();
        }

        Condition.wait(() -> !Inventory.isFull(), 100, 10);
        Bank.close();
    }

    private void tapAllGems() {
        Inventory.tapItem(ItemList.UNCUT_OPAL_1625, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_JADE_1627, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_RED_TOPAZ_1629, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_SAPPHIRE_1623, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_SAPPHIRE_1623, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_EMERALD_1621, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_RUBY_1619, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_DIAMOND_1617, false, 0.60);
    }
    private void tapAllClues() {
        Inventory.tapItem(ItemList.CLUE_GEODE_BEGINNER_23442, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_EASY_20358, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_MEDIUM_20360, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_HARD_20362, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_ELITE_20364, false, 0.60);
    }
}
