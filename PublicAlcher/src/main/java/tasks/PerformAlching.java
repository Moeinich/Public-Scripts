package tasks;

import main.PublicAlcher;
import utils.Task;

import java.awt.*;

import static helpers.Interfaces.*;
import static main.PublicAlcher.itemID;
import static tasks.CheckForItems.checkedForItems;

public class PerformAlching extends Task {

    public boolean activate() {
        return checkedForItems;
    }
    private final Rectangle highalchRect = new Rectangle(765, 328, 12, 12);
    private final Rectangle lowalchRect = new Rectangle(764, 255, 13, 13);

    @Override
    public boolean execute() {
        if (!Inventory.contains(itemID, 0.69) && GameTabs.isInventoryTabOpen()) {
            Logger.log("Ran out of items to alch, stopping script");
            Logout.logout();
            Script.stop();
            return true;
        }

        // Open the Magic tab if it is not already open
        if (!GameTabs.isMagicTabOpen()) {
            Logger.log("Opening Magic tab");
            GameTabs.openMagicTab();
            Condition.wait(GameTabs::isMagicTabOpen, 100, 10);
        }

        // Tap the Alchemy spell based on the magic level in the Magic tab
        if (GameTabs.isMagicTabOpen()) {
            if (PublicAlcher.alchemySpell.equals("High Level Alchemy")) {
                Logger.log("Pressing High Alchemy spell");
                //Magic.tapHighLevelAlchemySpell();
                Client.tap(highalchRect);
                Condition.wait(GameTabs::isInventoryTabOpen, 100, 40);
            } else {
                Logger.log("Pressing Low Alchemy spell");
                //Magic.tapLowLevelAlchemySpell();
                Client.tap(lowalchRect);
                Condition.wait(GameTabs::isInventoryTabOpen, 100, 40);
            }
        }

        // Tap the item in the Inventory
        if (GameTabs.isInventoryTabOpen()) {
            Logger.log("Pressing item in inventory");
            Inventory.tapItem(itemID, true, 0.69);
            // Need to wait for magic tab to open again, repeating the process
            Condition.wait(GameTabs::isMagicTabOpen, 100, 40);
            XpBar.getXP();
            return true;
        }

        return false;
    }
}
