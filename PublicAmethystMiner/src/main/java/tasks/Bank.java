package tasks;

import helpers.utils.ItemList;
import helpers.utils.Tile;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAmethystMiner.*;

public class Bank extends Task {
    Tile location;
    String dynamicBank = null;
    Tile bankTile = new Tile(12051, 38621, 0);

    @Override
    public boolean activate() {
        return !cutAmethysts && Inventory.isFull();
    }

    @Override
    public boolean execute() {
        location = Walker.getPlayerPosition();

        if (!Player.isTileWithinArea(location, bankArea)) {
            Logger.log("Not at the bank, walking there");
            Logger.log("walking to bank area");
            Walker.walkPath(bankPath);
            Condition.wait(() -> Player.within(bankArea), 200, 10);
            return true;
        }

        return handleBanking();
    }

    private boolean handleBanking() {
        Logger.log("Banking ores!");

        if (Player.isTileWithinArea(location, bankArea)) {
            ensureBankIsOpenAndReady();
            if (Bank.isOpen()) {
                bankItems();
                return true;
            }
        }

        return false;
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
            dynamicBank = Bank.setupDynamicBank();
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

        Inventory.tapItem(ItemList.AMETHYST_21347, 0.60);


        Condition.wait(() -> !Inventory.isFull(), 100, 10);
        Bank.close();
    }
}
