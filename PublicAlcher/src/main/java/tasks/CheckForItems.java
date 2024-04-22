package tasks;

import helpers.utils.ItemList;
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
        Logger.log("Checking for pickaxe");

        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 50, 10);
        }

        if (GameTabs.isInventoryTabOpen()) {
            boolean hasNats = Inventory.contains(ItemList.NATURE_RUNE_561, 0.60);
            checkedForNats = true;
            boolean hasAlchItem = Inventory.contains(itemID, 0.60);
            checkedForItem = true;

            if (hasNats && hasAlchItem) {
                checkedForItems = true;
                return true;
            }
        }

        Logger.log("Nats or Item not found, stopping script");
        Script.stop();
        return false;
    }
}
