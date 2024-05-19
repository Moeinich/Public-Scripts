package tasks;

import helpers.utils.ItemList;
import helpers.utils.Tile;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAmethystMiner.*;

public class Bank extends Task {
    Tile location;
    String dynamicBank;

    @Override
    public boolean activate() {
        return !cutAmethysts && Inventory.isFull() && !Player.within(bankArea);
    }

    @Override
    public boolean execute() {
        handleBanking();
        return false;
    }

    private void handleBanking() {
        Logger.log("Banking ores!");
        location = Walker.getPlayerPosition();

        if (!Player.isTileWithinArea(location, bankArea)) {
            Logger.log("Not at the bank, walking there");
            Walker.walkPath(miningGuild, bankPath);
        }

        if (Player.isTileWithinArea(location, bankArea)) {
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

        Inventory.tapItem(ItemList.AMETHYST_21347, 0.60);


        Condition.wait(() -> !Inventory.isFull(), 100, 10);
        Bank.close();
    }
}
