package tasks;

import helpers.utils.Tile;
import utils.Task;

import java.awt.*;

import static helpers.Interfaces.*;
import static main.PublicMasterFarmer.foodID;
import static main.PublicMasterFarmer.getRandomInt;

public class Bank extends Task {
    public static final Tile bankTile = new Tile(6875, 13609, 0); //TODO: this is not correct.
    private String bank;

    @Override
    public boolean activate() {
        return !Inventory.contains(foodID, 0.75);
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
            withdrawFood();
            closeBank();

        }
        return false;
    }

    private void withdrawFood() {
        setCustomQuantity();
        Bank.withdrawItem(foodID, 0.8);
    }

    private void setCustomQuantity() {
        if (!Bank.isSelectedQuantityCustomButton()) {
            Rectangle customQty = Bank.findQuantityCustomButton();
            Client.longPress(customQty);
            Condition.sleep(getRandomInt(300, 500));
            Client.tap(393, 499);
            Condition.sleep(getRandomInt(500, 800));
            Client.sendKeystroke("KEYCODE_5");
            Client.sendKeystroke("KEYCODE_ENTER");
            Logger.debugLog("Set custom quantity 14 for items in the bank.");
            Condition.wait(() -> Bank.isSelectedQuantityCustomButton(), 200, 12);
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
