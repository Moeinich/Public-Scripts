package tasks;

import helpers.utils.EquipmentSlot;
import helpers.utils.ItemList;
import main.PublicTeakChopper;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicTeakChopper.axeInventorySlotNumber;
import static main.PublicTeakChopper.axeEquipped;

public class CheckEquipment extends Task {
    public static boolean hasAxe = false;
    boolean checkedForAxe = false;
    boolean checkedInventory = false;
    boolean checkedEquipment = false;
    int[] axeIDs = { //Reversed order to check highest pickaxes first instead of lower ones.
            ItemList.CRYSTAL_AXE_23673,
            ItemList.CRYSTAL_AXE__INACTIVE__23675,
            ItemList.CRYSTAL_FELLING_AXE_28220,
            ItemList.CRYSTAL_FELLING_AXE__INACTIVE__28223,
            ItemList.INFERNAL_AXE_13241,
            ItemList.INFERNAL_AXE__UNCHARGED__13242,
            ItemList._3RD_AGE_AXE_20011,
            ItemList._3RD_AGE_FELLING_AXE_28226,
            ItemList.DRAGON_AXE_6739,
            ItemList.DRAGON_FELLING_AXE_28217,
            ItemList.RUNE_AXE_1359,
            ItemList.RUNE_FELLING_AXE_28214,
            ItemList.ADAMANT_AXE_1357,
            ItemList.ADAMANT_FELLING_AXE_28211,
            ItemList.MITHRIL_AXE_1355,
            ItemList.MITHRIL_FELLING_AXE_28208,
            ItemList.BLACK_AXE_1361,
            ItemList.BLACK_FELLING_AXE_28205,
            ItemList.STEEL_AXE_1353,
            ItemList.STEEL_FELLING_AXE_28202,
            ItemList.IRON_AXE_1349,
            ItemList.IRON_FELLING_AXE_28199,
            ItemList.BRONZE_AXE_1351,
            ItemList.BRONZE_FELLING_AXE_28196
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
                    axeInventorySlotNumber = Inventory.itemSlotPosition(axeIDs, 0.75);
                    Logger.log("Axe in inventory at slot " + axeInventorySlotNumber + ", continuing");
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
                        axeEquipped = true;
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
