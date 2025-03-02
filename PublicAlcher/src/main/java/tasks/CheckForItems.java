package tasks;

import helpers.utils.ItemList;
import helpers.utils.UITabs;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAlcher.itemID;

public class CheckForItems extends Task {
    public static boolean checkedForItems = false;
    boolean checkedForNats = false;
    boolean checkedForItem = false;

    public boolean activate() {
        return !checkedForItems;
    }

    @Override
    public boolean execute() {
        Logger.log("Checking your inventory for required items");

        if (!GameTabs.isTabOpen(UITabs.INVENTORY)) {
            GameTabs.openTab(UITabs.INVENTORY);
            Condition.wait(() -> GameTabs.isTabOpen(UITabs.INVENTORY), 50, 10);
        }

        boolean hasNats = Inventory.contains(ItemList.NATURE_RUNE_561, 0.69);
        checkedForNats = true;
        boolean hasAlchItem = Inventory.contains(itemID, 0.69);
        checkedForItem = true;

        if (hasNats && hasAlchItem) {
            checkedForItems = true;
            return true;
        } else {
            Logger.log("Nats or Item not found, stopping script");
            Script.stop();
            return false;
        }
    }
}
