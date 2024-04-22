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

    int natCountCache = 0;
    int itemCountCache = 0;
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public PerformAlching() {
        scheduler.scheduleWithFixedDelay(this::updateCountAndCheck, 0, 5, TimeUnit.MINUTES);
    }

    public boolean activate() {
        return checkedForItems;
    }

    @Override
    public boolean execute() {
        // If script is stopping, shut down the scheduler!
        if (Script.isScriptStopping()) {
            shutdownScheduler();
            return false;
        }

        // Handle Magic tab case
        if (GameTabs.isMagicTabOpen()) {
            Logger.log("Pressing High Alchemy spell");
            Magic.tapHighLevelAlchemySpell();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 40);
            return true;
        }

        // Handle Inventory tab case
        if (GameTabs.isInventoryTabOpen()) {
            Logger.log("Pressing item in inventory");
            Inventory.tapItem(itemID, true, 0.60);
            Condition.wait(() -> GameTabs.isMagicTabOpen(), 100, 40);
            return true;
        }

        //Fallback open inventory
        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 20);
            return true;
        }

        // Nothing to do? no problem, return and start over.
        return false;
    }
    
    // Keep count on how many items we have left to alch.
    public void updateCountAndCheck() {
        updateCountCache();
        if (natCountCache < 10 || itemCountCache < 10) {
            // If counts are low, increase checking frequency to every 10 seconds
            scheduler.scheduleWithFixedDelay(this::updateCountAndCheck, 0, 10, TimeUnit.SECONDS);
        }
        if (natCountCache < 1 || itemCountCache < 1) {
            // Stop the script if counts are very low or empty
            Logger.log("Running out of items or runes, stopping script.");
            Script.stop();
        }
    }

    private void updateCountCache() {
        natCountCache = Inventory.stackSize(ItemList.NATURE_RUNE_561);
        itemCountCache = Inventory.stackSize(itemID);
    }

    private void shutdownScheduler() {
        scheduler.shutdown();
        try {
            // Wait a while for existing tasks to terminate
            if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                // Cancel currently executing tasks
                scheduler.shutdownNow();
            }
        } catch (InterruptedException ie) {
            // (Re-)Cancel if current thread also interrupted
            scheduler.shutdownNow();
            // Preserve interrupt status
            Thread.currentThread().interrupt();
        }
    }
}
