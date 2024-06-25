package tasks;

import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAlcher.itemID;
import static tasks.CheckForItems.checkedForItems;

public class PerformAlching extends Task {

    private int natCountCache;
    private int itemCountCache;
    private final int previousNatCountCache = 0;
    private final int previousItemCountCache = 0;
    private final boolean doneInitialCheck = false;


    public boolean activate() {
        return checkedForItems;
    }

    @Override
    public boolean execute() {
        if (!Inventory.contains(itemID, 0.80)) {
            Logger.log("Ran out of items to alch, stopping script");
            Logout.logout();
            Script.stop();
        }

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
            Inventory.tapItem(itemID, true, 0.60);
            // Need to wait for magic tab to open again, repeating the process
            Condition.wait(GameTabs::isMagicTabOpen, 100, 40);
            XpBar.getXP();
            return true;
        }

        return false;
    }
}
