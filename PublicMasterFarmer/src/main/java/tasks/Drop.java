package tasks;

import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMasterFarmer.dropList;

public class Drop extends Task {

    public boolean activate() {
        return Inventory.isFull();
    }

    @Override
    public boolean execute() {
        if (!GameTabs.isInventoryTabOpen()) {
            Logger.log("Opening inventory");
            GameTabs.openInventoryTab();
        }

        if (!Game.isTapToDropEnabled()) {
            Logger.log("Enabling tap to drop");
            Game.enableTapToDrop();
            Condition.wait(Game::isTapToDropEnabled, 50, 10);
            Logger.log("Tap to drop enabled");
            return true;
        }

        Inventory.tapAllItems(dropList, 0.80);

        return true;
    }
}
