package tasks;


import helpers.utils.ItemList;
import utils.Task;
import java.util.Random;

import static helpers.Interfaces.*;
import static main.PublicMiner.*;

public class handleCluesAndGems extends Task {
    Random rand = new Random();

    public boolean activate() {
        if (!dropCluesAndGems) {
            return false;
        }

        boolean hasClues = Inventory.containsAny(gemIDs, 0.60);
        boolean hasGems = Inventory.containsAny(clueIDs, 0.60);

        return Inventory.isFull() && (hasClues || hasGems);
    }

    @Override
    public boolean execute() {
        if (rand.nextInt(10) < 8) { // 80% chance to skip execution
            return false;
        }

        return dropCluesAndGems && (dropCluesIfNeeded() || dropGemsIfNeeded());
    }

    private boolean dropGemsIfNeeded() {
        if (Inventory.containsAny(gemIDs, 0.60)) {
            Logger.log("Dropping gems");
            tapAllGems();
            Condition.wait(() -> !Inventory.containsAny(gemIDs, 0.60), 100, 20);
            return true;
        }
        return false;
    }

    private void tapAllGems() {
        Inventory.tapItem(ItemList.UNCUT_OPAL_1625, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_JADE_1627, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_RED_TOPAZ_1629, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_SAPPHIRE_1623, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_SAPPHIRE_1623, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_EMERALD_1621, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_RUBY_1619, false, 0.60);
        Inventory.tapItem(ItemList.UNCUT_DIAMOND_1617, false, 0.60);
    }

    private boolean dropCluesIfNeeded() {
        if (Inventory.containsAny(clueIDs, 0.60)) {
            Logger.log("Dropping clues");
            tapAllClues();
            Condition.wait(() -> !Inventory.containsAny(clueIDs, 0.60), 100, 20);
            return true;
        }
        return false;
    }

    private void tapAllClues() {
        Inventory.tapItem(ItemList.CLUE_GEODE_BEGINNER_23442, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_EASY_20358, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_MEDIUM_20360, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_HARD_20362, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_ELITE_20364, false, 0.60);
    }
}
