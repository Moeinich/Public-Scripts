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

        if (!Game.isTapToDropEnabled()) {
            Logger.log("Enabling tap to drop");
            Game.enableTapToDrop();
            Condition.wait(() -> Game.isTapToDropEnabled(), 50, 10);
            Logger.log("Tap to drop enabled");
            return true;
        }

        if (!dropCluesAndGems) {
            boolean inventoryHasOres = Inventory.contains(oreTypeInt, 0.60);
            // Drop the items
            if (inventoryHasOres) {
                Logger.log("Dropping ores");
                Inventory.tapAllItems(oreTypeInt, 0.60);
                return true;
            }
        } else {
            if (pickaxeInventorySlotNumber != 0) {
                Logger.log("Dropping..");
                Inventory.dropInventItems(pickaxeInventorySlotNumber, true);
                return true;
            } else {
                Logger.log("Pickaxe slot # is not set");
            }
        }

        if (Inventory.isFull()) {
            Logger.log("Your inventory is still full, we cannot drop anything!");
            Logout.logout();
            Script.stop();
        }

        return false;
    }


}
