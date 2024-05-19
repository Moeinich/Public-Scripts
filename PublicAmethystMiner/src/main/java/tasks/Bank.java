package tasks;

import helpers.utils.ItemList;
import helpers.utils.Tile;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAmethystMiner.*;

public class Bank extends Task {
    Tile location;
    String dynamicBank;
    Tile bankTile = new Tile(2129,210);

    @Override
    public boolean activate() {
        return !cutAmethysts && Inventory.isFull();
    }

    @Override
    public boolean execute() {
        handleBanking();
        return false;
    }

    private boolean handleBanking() {
        Logger.log("Banking ores!");
        location = Walker.getPlayerPosition();

        if (!Player.isTileWithinArea(location, bankArea)) {
            Logger.log("Not at the bank, walking there");
            if (Player.isTileWithinArea(location, mineArea)) {
                Logger.log("walking to bank area");
                Walker.walkPath(miningGuild, bankPath);
                Condition.wait(() -> Player.within(bankArea, miningGuild), 200, 10);
                return true;
            }

            if (Player.within(bankArea) && !Player.atTile(bankTile)) {
                Logger.log("Stepping to bank tile");
                Walker.step(bankTile, miningGuild);
                Condition.wait(() -> Player.atTile(bankTile, miningGuild), 200, 10);
                return true;
            }
        }

        if (Player.isTileWithinArea(location, bankArea)) {
            ensureBankIsReady();
            if (Bank.isOpen()) {
                bankItems();
                return true;
            }
        }

        return false;
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
