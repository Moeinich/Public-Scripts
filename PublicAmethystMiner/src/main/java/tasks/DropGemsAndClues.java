package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicAmethystMiner.clueIDs;
import static main.PublicAmethystMiner.gemIDs;

public class DropGemsAndClues extends Task {
    @Override
    public boolean activate() {
        return Inventory.isFull() && (Inventory.containsAny(clueIDs, 0.60) || Inventory.containsAny(gemIDs, 0.60));
    }

    @Override
    public boolean execute() {
        if (!Game.isTapToDropEnabled()) {
            Game.enableTapToDrop();
            Condition.wait(() -> Game.isTapToDropEnabled(), 100, 20);
        }
        tapAllClues();
        tapAllGems();

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
        Inventory.tapItem(ItemList.CLUE_GEODE__BEGINNER__23442, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE__EASY__20358, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE__MEDIUM__20360, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE__HARD__20362, false, 0.60);
        Inventory.tapItem(ItemList.CLUE_GEODE__ELITE__20364, false, 0.60);
    }
}
