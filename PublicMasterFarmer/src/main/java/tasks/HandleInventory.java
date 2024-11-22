package tasks;

import utils.Task;

import static helpers.Interfaces.*;

public class HandleInventory extends Task {

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



        return false;
    }
}
