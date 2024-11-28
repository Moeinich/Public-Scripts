package tasks;

import helpers.utils.ItemPair;
import helpers.utils.Tile;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMasterFarmer.*;

public class Bank extends Task {
    public static final Tile bankTile = new Tile(6875, 13609, 0); //TODO: this is not correct.
    private String bank;

    @Override
    public boolean activate() {
        return !Inventory.contains(foodID, 0.85);
    }

    @Override
    public boolean execute() {
        if (!Player.atTile(bankTile)) {
            Walker.webWalk(bankTile);
            return true;
        } else {
            if (bank == null) {
                bank = Bank.setupDynamicBank();
            }
            openBank();
            depositLoot();
            withdrawFood();
            closeBank();

        }
        return false;
    }

    private void depositLoot() {
        for (ItemPair itemID : keepList) {
            Inventory.tapAllItems(itemID.getItemID(), 0.85);
        }
    }

    private void withdrawFood() {
        setCustomQuantity();
        Bank.withdrawItem(foodID, 0.8);
    }

    private void setCustomQuantity() {
        if (!Bank.isSelectedQuantityCustomButton()) {
            Bank.setCustomQuantity(3);
        }
    }

    private void closeBank() {
        if (Bank.isOpen()) {
            Bank.close();
            Condition.wait(() -> !Bank.isOpen(), 500, 10);
        }
    }

    private void openBank() {
        if (!Bank.isOpen()) {
            Bank.open(bank);
            Condition.wait(() -> Bank.isOpen(), 500, 10);
        }
    }
}
