package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAlcher.itemID;
import static tasks.CheckForItems.checkedForItems;

public class PerformAlching extends Task {

    private int natCountCache = 100;
    private int itemCountCache = 100;
    private int previousNatCountCache = 0;
    private int previousItemCountCache = 0;

    public boolean activate() {
        return checkedForItems;
    }

    @Override
    public boolean execute() {
        if (natCountCache > 1 && itemCountCache > 1) {
            // Open the Magic tab if it is not already open
            if (!GameTabs.isMagicTabOpen()) {
                Logger.log("Opening Magic tab");
                GameTabs.openMagicTab();
                Condition.wait(GameTabs::isMagicTabOpen, 100, 10);
            }

            // Tap the High Alchemy spell in the Magic tab
            if (GameTabs.isMagicTabOpen()) {
                Logger.log("Pressing High Alchemy spell");
                Magic.tapHighLevelAlchemySpell();
                Condition.wait(GameTabs::isInventoryTabOpen, 100, 40);
            }

            // Tap the item in the Inventory
            if (GameTabs.isInventoryTabOpen()) {
                Logger.log("Pressing item in inventory");
                updateCountCache();
                Inventory.tapItem(itemID, true, 0.60);
                // Assume need to wait for magic tab to open again, repeating the process
                Condition.wait(GameTabs::isMagicTabOpen, 100, 40);
                XpBar.getXP();
                return true;
            }
        } else {
            Script.stop();
        }

        return false;
    }

    private void updateCountCache() {
        // Fetch current counts from the inventory
        int currentNatCount = Inventory.stackSize(ItemList.NATURE_RUNE_561);
        int currentItemCount = Inventory.stackSize(itemID);

        // Log current counts for debugging
        Logger.debugLog("Current Nat count: " + currentNatCount);
        Logger.debugLog("Current Item count: " + currentItemCount);

        // Check if the count has dropped by more than 5 since last update
        if (Math.abs(previousNatCountCache - currentNatCount) <= 5) {
            natCountCache = currentNatCount;
            previousNatCountCache = currentNatCount;
        } else {
            Logger.debugLog("Nat count change too large, not updating cache.");
        }

        if (Math.abs(previousItemCountCache - currentItemCount) <= 5) {
            itemCountCache = currentItemCount;
            previousItemCountCache = currentItemCount;
        } else {
            Logger.debugLog("Item count change too large, not updating cache.");
        }
    }
}
