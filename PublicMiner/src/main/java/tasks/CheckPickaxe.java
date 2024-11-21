package tasks;

import helpers.utils.EquipmentSlot;
import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;
import static main.PublicMiner.pickaxeEquipped;

public class CheckPickaxe extends Task {
    public static boolean hasPickaxe = false;
    public static boolean useSpecial = false;

    boolean checkedForPickaxe = false;
    boolean checkedInventory = false;
    boolean checkedEquipment = false;
    int[] pickaxeIDs = { //Reversed order to check highest pickaxes first instead of lower ones.
            ItemList.DRAGON_PICKAXE_11920,
            ItemList.RUNE_PICKAXE_1275,
            ItemList.ADAMANT_PICKAXE_1271,
            ItemList.MITHRIL_PICKAXE_1273,
            ItemList.BLACK_PICKAXE_12297,
            ItemList.STEEL_PICKAXE_1269,
            ItemList.IRON_PICKAXE_1267,
            ItemList.BRONZE_PICKAXE_1265,
            ItemList._3RD_AGE_PICKAXE_20014,
            ItemList.INFERNAL_PICKAXE_13243,
            ItemList.CRYSTAL_PICKAXE_23680,
            ItemList.DRAGON_PICKAXE__OR__23677,
            ItemList.DRAGON_PICKAXE__OR__25376,
            ItemList.DRAGON_PICKAXE_12797,
            ItemList.INFERNAL_PICKAXE__UNCHARGED__13244,
            ItemList.INFERNAL_PICKAXE__OR__25063,
            ItemList.CRYSTAL_PICKAXE__INACTIVE__23682,
            ItemList.GILDED_PICKAXE_23276,
    };

    int[] specialPickaxes = {
            ItemList._3RD_AGE_PICKAXE_20014,
            ItemList.INFERNAL_PICKAXE_13243,
            ItemList.INFERNAL_PICKAXE__UNCHARGED__13244,
            ItemList.INFERNAL_PICKAXE__OR__25063,
            ItemList.CRYSTAL_PICKAXE_23680,
            ItemList.CRYSTAL_PICKAXE__INACTIVE__23682,
            ItemList.DRAGON_PICKAXE_11920,
            ItemList.DRAGON_PICKAXE__OR__23677,
            ItemList.DRAGON_PICKAXE__OR__25376,
            ItemList.DRAGON_PICKAXE_12797
    };

    public boolean activate() {
        return !checkedForPickaxe;
    }

    @Override
    public boolean execute() {
        Logger.log("Checking for pickaxe");
        if (!checkedInventory) {
            if (!GameTabs.isInventoryTabOpen()) {
                GameTabs.openInventoryTab();
                Condition.wait(() -> GameTabs.isInventoryTabOpen(), 200, 10);
            }

            if (GameTabs.isInventoryTabOpen()) {
                for (int pickaxeID : pickaxeIDs) {
                    if (Inventory.contains(pickaxeID, 0.75)) {
                        if (isUseSpecialPickaxe(pickaxeID)) {
                            useSpecial = true;
                        }
                        hasPickaxe = true;
                        checkedForPickaxe = true;
                        Logger.log("Pickaxe(" + pickaxeID + ") in inventory, continuing");
                        return true;
                    }
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
                for (int pickaxeID : pickaxeIDs) {
                    if (Equipment.itemAt(EquipmentSlot.WEAPON, pickaxeID)) {
                        if (isUseSpecialPickaxe(pickaxeID)) {
                            useSpecial = true;
                        }
                        hasPickaxe = true;
                        checkedForPickaxe = true;
                        pickaxeEquipped = true;
                        Logger.log("Pickaxe(" + pickaxeID + ") equipped, continuing");
                        return true;
                    }
                }
            }
            checkedEquipment = true;
        }

        checkedForPickaxe = true;
        Logger.log("Pickaxe not found, stopping script");
        Script.stop();
        return false;
    }

    private boolean isUseSpecialPickaxe(int itemID) {
        for (int pickaxeID : specialPickaxes) {
            if (itemID == pickaxeID) {
                return true;
            }
        }
        return false;
    }
}
