package tasks;

import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMiner.bankOres;
import static main.PublicMiner.slotsToSafeConfig;

public class DropOres extends Task {

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
            Condition.wait(Game::isTapToDropEnabled, 50, 10);
            Logger.log("Tap to drop enabled");
            return true;
        }

        Logger.log("Dropping items except for safe slots...");

        int slotsToSafe;
        try {
            slotsToSafe = slotsToSafeConfig;
        } catch (NumberFormatException e) {
            Logger.log("Invalid Slots to Safe value, defaulting to 0.");
            slotsToSafe = 0;
        }

        int totalSlots = 28;  // Slots range from 1 to 28
        int excludedStartIndex = totalSlots - slotsToSafe;

        // Drop all items in slots from 1 to excludedStartIndex (inclusive)
        for (int i = 1; i <= excludedStartIndex; i++) {
            if (Script.isScriptStopping()) break;  // Stop if the script is being terminated
            Inventory.tapItem(i);
            Condition.sleep(100);
        }

        if (Inventory.isFull()) {
            Logger.log("Inventory is still full after dropping, stopping script.");
            Logout.logout();
            Script.stop();
        }

        return false;
    }
}

