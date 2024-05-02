package tasks;

import helpers.utils.EquipmentSlot;
import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;

public class CheckEquipment extends Task {
    public static boolean hasAxe = false;
    boolean checkedForAxe = false;
    boolean checkedInventory = false;
    boolean checkedEquipment = false;
    int[] axeIDs = { //Reversed order to check highest pickaxes first instead of lower ones.
            ItemList.DRAGON_AXE_6739,
            ItemList.RUNE_AXE_1359,
            ItemList.ADAMANT_AXE_1357,
            ItemList.MITHRIL_AXE_1355,
            ItemList.BLACK_AXE_1361,
            ItemList.STEEL_AXE_1353,
            ItemList.IRON_AXE_1349,
            ItemList.BRONZE_AXE_1351
    };

    public boolean activate() {
        return !checkedForAxe;
    }

    @Override
    public boolean execute() {
        Logger.log("Checking for axe");
        if (!checkedInventory) {
            if (!GameTabs.isInventoryTabOpen()) {
                GameTabs.openInventoryTab();
                Condition.wait(() -> GameTabs.isInventoryTabOpen(), 50, 10);
            }

            if (GameTabs.isInventoryTabOpen()) {
                if (Inventory.containsAny(axeIDs, 0.75)) {
                    hasAxe = true;
                    checkedForAxe = true;
                    Logger.log("Axe in inventory, continuing");
                    return true;
                }
            }
            checkedInventory = true;
        }

        if (!checkedEquipment) {
            if (!GameTabs.isEquipTabOpen()) {
                GameTabs.openEquipTab();
                Condition.wait(() -> GameTabs.isEquipTabOpen(), 50, 10);
            }

            if (GameTabs.isEquipTabOpen()) {
                for (int pickaxeID : axeIDs) {
                    if (Equipment.itemAt(EquipmentSlot.WEAPON, pickaxeID)) {
                        hasAxe = true;
                        checkedForAxe = true;
                        Logger.log("Axe equipped, continuing");
                        return true;
                    }
                }
            }
            checkedEquipment = true;
        }

        checkedForAxe = true;
        Logger.log("Axe not found, stopping script");
        Script.stop();
        return false;
    }
}
