package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicTeakChopper.axeEquipped;
import static main.PublicTeakChopper.axeInventorySlotNumber;

public class DropLogs extends Task {
    @Override
    public boolean activate() {
        return Inventory.isFull();
    }

    @Override
    public boolean execute() {
        Logger.log("Inventory full, dropping logs!");
        if (!Game.isTapToDropEnabled()) {
            Game.enableTapToDrop();
            Condition.wait(() -> Game.isTapToDropEnabled(), 50, 10);
        }

//        if (axeInventorySlotNumber != 0) {
//            Inventory.dropInventItems(axeInventorySlotNumber, true);
//        } else if (axeEquipped) {
//            Inventory.dropInventItems(0, true);
//        } else {
//            Inventory.tapAllItems(ItemList.TEAK_LOGS_6333, 0.80);
//        }
        Inventory.tapAllItems(ItemList.TEAK_LOGS_6333, 0.80);

        return true;
    }
}
