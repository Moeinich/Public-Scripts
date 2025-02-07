package tasks;

import helpers.utils.EquipmentSlot;
import helpers.utils.ItemList;
import helpers.utils.UITabs;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicTeakChopper.axeEquipped;

public class CheckEquipment extends Task {
    public static boolean hasAxe = false;
    boolean checkedForAxe = false;
    boolean checkedInventory = false;
    boolean checkedEquipment = false;

    public static boolean useSpecial = false;

    int[] axeIDs = { //Reversed order to check highest pickaxes first instead of lower ones.
            ItemList.DRAGON_AXE_6739,
            ItemList.RUNE_AXE_1359,
            ItemList.ADAMANT_AXE_1357,
            ItemList.MITHRIL_AXE_1355,
            ItemList.BLACK_AXE_1361,
            ItemList.STEEL_AXE_1353,
            ItemList.IRON_AXE_1349,
            ItemList.BRONZE_AXE_1351,
            ItemList.CRYSTAL_AXE_23673,
            ItemList.CRYSTAL_AXE_INACTIVE_23675,
            ItemList.CRYSTAL_FELLING_AXE_28220,
            ItemList.CRYSTAL_FELLING_AXE_INACTIVE_28223,
            ItemList.INFERNAL_AXE_13241,
            ItemList.INFERNAL_AXE_UNCHARGED_13242,
            ItemList._3RD_AGE_AXE_20011,
            ItemList._3RD_AGE_FELLING_AXE_28226,
            ItemList.DRAGON_FELLING_AXE_28217,
            ItemList.RUNE_FELLING_AXE_28214,
            ItemList.ADAMANT_FELLING_AXE_28211,
            ItemList.MITHRIL_FELLING_AXE_28208,
            ItemList.BLACK_FELLING_AXE_28205,
            ItemList.STEEL_FELLING_AXE_28202,
            ItemList.IRON_FELLING_AXE_28199,
            ItemList.BRONZE_FELLING_AXE_28196,
    };

    int[] specialAxes = {
            ItemList.CRYSTAL_AXE_23673,
            ItemList.CRYSTAL_AXE_INACTIVE_23675,
            ItemList.CRYSTAL_FELLING_AXE_28220,
            ItemList.CRYSTAL_FELLING_AXE_INACTIVE_28223,
            ItemList.INFERNAL_AXE_13241,
            ItemList.INFERNAL_AXE_UNCHARGED_13242,
            ItemList._3RD_AGE_AXE_20011,
            ItemList._3RD_AGE_FELLING_AXE_28226,
            ItemList.DRAGON_AXE_6739,
            ItemList.DRAGON_FELLING_AXE_28217,
    };

    public boolean activate() {
        return !checkedForAxe;
    }

    @Override
    public boolean execute() {
        Logger.log("Checking for axe");
        if (!checkedInventory) {
            if (!GameTabs.isTabOpen(UITabs.INVENTORY)) {
                GameTabs.openTab(UITabs.INVENTORY);
                Condition.wait(() -> GameTabs.isTabOpen(UITabs.INVENTORY), 50, 10);
            }

            if (GameTabs.isTabOpen(UITabs.INVENTORY)) {
                for (int axeID : axeIDs) {
                    if (Inventory.contains(axeID, 0.75)) {
                        hasAxe = true;
                        checkedForAxe = true;
                        Logger.log("Axe(" + axeID + ") in inventory, continuing");
                        return true;
                    }
                }
            }
            checkedInventory = true;
        }

        if (!checkedEquipment) {
            if (!GameTabs.isTabOpen(UITabs.EQUIP)) {
                GameTabs.openTab(UITabs.EQUIP);
                Condition.wait(() -> GameTabs.isTabOpen(UITabs.EQUIP), 50, 10);
            }

            if (GameTabs.isTabOpen(UITabs.EQUIP)) {
                for (int axeID : axeIDs) {
                    if (Equipment.itemAt(EquipmentSlot.WEAPON, axeID)) {
                        if (isUseSpecialAxe(axeID)) {
                            useSpecial = true;
                        }
                        hasAxe = true;
                        checkedForAxe = true;
                        axeEquipped = true;
                        Logger.log("Axe(" + axeID + ") equipped, continuing");
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

    private boolean isUseSpecialAxe(int itemID) {
        for (int pickaxeID : specialAxes) {
            if (itemID == pickaxeID) {
                return true;
            }
        }
        return false;
    }
}
