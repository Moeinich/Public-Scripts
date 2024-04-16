package tasks;

import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;

public class DropOres  extends Task {
    public boolean activate() {
        // Early exit if banking is enabled!
        if (bankOres) {
            return false;
        }
        return Inventory.isFull();
    }
    @Override
    public boolean execute() {
        boolean isTapToDropEnabled = Game.isTapToDropEnabled();

        if (!isTapToDropEnabled) {
            Logger.log("Enabling tap to drop");
            Game.enableTapToDrop();
            Condition.wait(() -> isTapToDropEnabled, 50, 10);
            Logger.log("Tap to drop enabled");
            return true;
        }

        boolean inventoryHasOres = Inventory.contains(oreTypeInt, 0.60);
        // Drop the items
        if (isTapToDropEnabled && inventoryHasOres) {
            Logger.log("Dropping ores");
            Inventory.tapAllItems(oreTypeInt, 0.60);
            return true;
        }

        return false;
    }


}
