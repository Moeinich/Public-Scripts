package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.Inventory;
import static main.PublicAmethystMiner.clueIDs;
import static main.PublicAmethystMiner.gemIDs;

public class DropGemsAndClues extends Task {
    @Override
    public boolean activate() {
        return Inventory.isFull();
    }

    @Override
    public boolean execute() {
        if (Inventory.containsAny(clueIDs, 0.60)) {
            tapAllClues();
        }
        if(Inventory.containsAny(gemIDs, 0.60)) {
            tapAllGems();
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
    private void tapAllClues() {
        Inventory.tapItem(ItemList.CLUE_GEODE_BEGINNER_23442, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_EASY_20358, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_MEDIUM_20360, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_HARD_20362, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE_ELITE_20364, false, 0.60);
    }
}
