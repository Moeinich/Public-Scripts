package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;

public class CheckForItems extends Task {
    public static boolean checkedForItems = false;

    public boolean activate() {
        return !checkedForItems;
    }

    @Override
    public boolean execute() {
        Logger.log("Checking your inventory for required items");

        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 50, 10);
        }

        if (GameTabs.isInventoryTabOpen()) {
            boolean hasCannonballs = Inventory.contains(ItemList.CANNONBALL_2, 0.60);
            if (hasCannonballs) {
                checkedForItems = true;
                return true;
            }
        }

        Logger.log("Nats or Item not found, stopping script");
        Script.stop();
        return false;
    }
}
