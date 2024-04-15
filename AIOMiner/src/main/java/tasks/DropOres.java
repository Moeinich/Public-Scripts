package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;
import static main.AIOMiner.*;

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
        Logger.log("dropping...");
        boolean isTapToDropEnabled = Game.isTapToDropEnabled();

        if (!isTapToDropEnabled) {
            Logger.log("Enabling tap to drop");
            Game.enableTapToDrop();
            Condition.wait(() -> isTapToDropEnabled, 50, 10);
            Logger.log("Tap to drop enabled");
            return true;
        }

        if (dropClues) {
            boolean inventoryHasClues = Inventory.containsAny(clueIDs, 0.60);
            if (inventoryHasClues) {
                Logger.log("Dropping clues");
                Inventory.tapItem(ItemList.CLUE_GEODE_BEGINNER_23442, false, 0.60);
                Inventory.tapItem(ItemList.CLUE_GEODE_EASY_20358, false, 0.60);
                Inventory.tapItem(ItemList.CLUE_GEODE_MEDIUM_20360, false, 0.60);
                Inventory.tapItem(ItemList.CLUE_GEODE_HARD_20362, false, 0.60);
                Inventory.tapItem(ItemList.CLUE_GEODE_ELITE_20364, false, 0.60);

                Condition.wait(() -> !Inventory.containsAny(clueIDs, 0.60), 100, 20);
                return true;
            }
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
