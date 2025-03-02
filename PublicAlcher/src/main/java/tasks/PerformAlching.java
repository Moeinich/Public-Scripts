package tasks;

import helpers.utils.Spells;
import helpers.utils.UITabs;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAlcher.alchemySpell;
import static main.PublicAlcher.itemID;
import static tasks.CheckForItems.checkedForItems;

public class PerformAlching extends Task {
    private int cachedItemAmount = 0;
    public boolean activate() {
        return checkedForItems;
    }

    @Override
    public boolean execute() {

        if (cachedItemAmount <= 20) {
            if (!GameTabs.isTabOpen(UITabs.INVENTORY)) {
                GameTabs.openTab(UITabs.INVENTORY);
                Condition.wait(() -> GameTabs.isTabOpen(UITabs.INVENTORY), 100, 10);
            }

            cachedItemAmount = Inventory.count(itemID, 0.69);

            if (cachedItemAmount <= 0) {
                Logger.log("Ran out of items to alch, stopping script");
                Logout.logout();
                Script.stop();
                return true;
            }
        }


        // Open the Magic tab if it is not already open
        if (!GameTabs.isTabOpen(UITabs.MAGIC)) {
            Logger.log("Opening Magic tab");
            GameTabs.openTab(UITabs.MAGIC);
            Condition.wait(() -> GameTabs.isTabOpen(UITabs.MAGIC), 100, 10);
        }

        // Tap the Alchemy spell based on the magic level in the Magic tab
        if (GameTabs.isTabOpen(UITabs.MAGIC)) {
            Logger.log("Pressing " + alchemySpell);

            if (alchemySpell.equals("High Level Alchemy")) {
                Magic.castSpell(Spells.HIGH_LEVEL_ALCHEMY);
            } else {
                Magic.castSpell(Spells.LOW_LEVEL_ALCHEMY);
            }

            cachedItemAmount--;
            Condition.wait(() -> GameTabs.isTabOpen(UITabs.INVENTORY), 100, 40);
        }

        // Tap the item in the Inventory
        if (GameTabs.isTabOpen(UITabs.INVENTORY)) {
            Logger.log("Pressing item in inventory");
            Inventory.tapItem(itemID, true, 0.69);
            // Need to wait for magic tab to open again, repeating the process
            Condition.wait(() -> GameTabs.isTabOpen(UITabs.MAGIC), 100, 40);
            XpBar.getXP();
            return true;
        }

        return false;
    }
}
