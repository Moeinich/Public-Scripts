package tasks;

import utils.Task;

import java.awt.*;

import static helpers.Interfaces.*;
import static main.AIOMiner.*;

public class DropOres  extends Task {
    Rectangle tapToDropRect = new Rectangle(17, 192, 29, 19);
    Color tapToDropActiveColor = new Color(0xfecb65);

    public boolean activate() {
        // Early exit if banking is enabled!
        if (bankOres) {
            return false;
        }
        return Inventory.isFull();
    }
    @Override
    public boolean execute() {
        boolean isTapToDropEnabled = Client.isColorInRect(tapToDropActiveColor, tapToDropRect, 5);

        if (!isTapToDropEnabled) {
            Logger.log("Enabling tap to drop");
            Client.tap(tapToDropRect);
            Condition.wait(() -> isTapToDropEnabled, 50, 10);
            Logger.log("Tap to drop enabled");
            return true;
        }

        boolean inventoryHasOres = Inventory.contains(oreTypeInt, 0.60);
        Logger.log("Do we have the ores? " + inventoryHasOres);

        // Drop the items
        if (inventoryHasOres && isTapToDropEnabled) {
            Logger.log("Dropping ores");
            Inventory.tapAllItems(oreTypeInt, 0.60);
            return true;
        }

        return false;
    }
}
