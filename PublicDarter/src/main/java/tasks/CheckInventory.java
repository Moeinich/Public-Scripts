package tasks;

import helpers.utils.ItemList;
import utils.Task;

import static helpers.Interfaces.*;

public class CheckInventory extends Task {
    public static boolean checkedInventory = false;
    private boolean hasFeathers = false;
    private boolean hasDartTips = false;
    public static int featherInventorySpot = 0;
    public static int dartInventorySpot = 0;
    public static int foundDartID;

    public static int[] dartIDs = {
            ItemList.BRONZE_DART_TIP_819,
            ItemList.IRON_DART_TIP_820,
            ItemList.STEEL_DART_TIP_821,
            ItemList.MITHRIL_DART_TIP_822,
            ItemList.ADAMANT_DART_TIP_823,
            ItemList.RUNE_DART_TIP_824,
            ItemList.AMETHYST_DART_TIP_25853,
            ItemList.DRAGON_DART_TIP_11232,
    };


    @Override
    public boolean activate() {
        return !checkedInventory;
    }

    @Override
    public boolean execute() {
        Logger.log("Checking Inventory..");
        if (Inventory.contains(ItemList.FEATHER_314, 0.80)) {
            hasFeathers = true;
            featherInventorySpot = Inventory.itemSlotPosition(ItemList.FEATHER_314, 0.80);
            Logger.log("We have feathers in spot: " + featherInventorySpot);
        }

        // Loop over dartIDs to check inventory for each type of dart
        for (int dartID : dartIDs) {
            if (Inventory.contains(dartID, 0.80)) {
                hasDartTips = true;
                dartInventorySpot = Inventory.itemSlotPosition(dartID, 0.80);
                foundDartID = dartID;
                Logger.log("We found darts");
                break;
            }
        }
        checkedInventory = true;
        if (!hasFeathers || !hasDartTips) {
            Logger.log("We failed checking inventory.. stopping script");
            Script.stop();
        }

        return hasFeathers && hasDartTips;
    }
}
