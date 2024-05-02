package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;

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

        Inventory.tapAllItems(ItemList.TEAK_LOGS_6333, 0.80);
        return true;
    }
}
