package tasks;

import helpers.utils.ItemList;
import utils.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static helpers.Interfaces.*;
import static main.PublicTeakChopper.*;

public class DropLogs extends Task {
    private static List<Integer> cachedRandomSlots = null;

    @Override
    public boolean activate() {
        return Inventory.isFull();
    }

    @Override
    public boolean execute() {
        if (!GameTabs.isInventoryTabOpen()) {
            Logger.log("Opening inventory");
            GameTabs.openInventoryTab();
        }

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
        if (randomDropping) {
            if (cachedRandomSlots == null || cachedRandomSlots.size() != excludedStartIndex) {
                cachedRandomSlots = new ArrayList<>();
                for (int i = 1; i <= excludedStartIndex; i++) {
                    cachedRandomSlots.add(i);
                }
            }

            // Use cached shuffled slots for dropping
            Collections.shuffle(cachedRandomSlots);
            for (int slot : cachedRandomSlots) {
                if (Script.isScriptStopping()) break;  // Stop if the script is being terminated
                Inventory.tapItem(slot);
                Condition.sleep(generateRandomDelay(75, 125));
            }
        } else {
            for (int i = 1; i <= excludedStartIndex; i++) {
                if (Script.isScriptStopping()) break;  // Stop if the script is being terminated
                Inventory.tapItem(i);
                Condition.sleep(generateRandomDelay(75, 125));
            }
        }

        if (Inventory.isFull()) {
            Logger.log("Inventory is still full after dropping, stopping script.");
            Logout.logout();
            Script.stop();
        }

        return false;
    }
}
