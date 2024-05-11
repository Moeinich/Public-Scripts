package tasks;

import utils.Task;

import java.util.Arrays;
import java.util.List;

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

        if (Inventory.contains(21341, 0.75)) {
            unidentifiedMineralsInventorySpot = Inventory.itemSlotPosition(21341,0.75);
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
                if (unidentifiedMineralsInventorySpot != 0) {
                    Inventory.dropInventItems(Arrays.asList(unidentifiedMineralsInventorySpot, pickaxeInventorySlotNumber), true);
                } else {
                    Inventory.dropInventItems(pickaxeInventorySlotNumber, true);
                }
                return true;
            } else if (pickaxeEquipped) {
                Logger.log("Dropping..");
                if (unidentifiedMineralsInventorySpot != 0) {
                    Inventory.dropInventItems(unidentifiedMineralsInventorySpot, true);
                } else {
                    Inventory.dropInventItems(0, true);
                }
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
