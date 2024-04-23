package tasks;

import helpers.utils.ItemList;
import utils.Task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static helpers.Interfaces.*;
import static main.PublicAlcher.itemID;
import static tasks.CheckForItems.checkedForItems;

public class PerformAlching extends Task {

    int natCountCache = 100;
    int itemCountCache = 100;

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
        natCountCache = Inventory.stackSize(ItemList.NATURE_RUNE_561);
        itemCountCache = Inventory.stackSize(itemID);
        Logger.debugLog("Nat count: " + natCountCache);
        Logger.debugLog("Item count: " + itemCountCache);
    }
}
