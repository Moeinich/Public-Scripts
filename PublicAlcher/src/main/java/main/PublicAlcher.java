package main;

import helpers.*;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.ItemList;
import helpers.utils.OptionType;
import tasks.CheckForItems;
import tasks.PerformAlching;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;


@ScriptManifest(
        name = "PublicAlcher",
        description = "An easy to use Alcher",
        version = "1.0",
        guideLink = "",
        categories = {ScriptCategory.Magic}
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "Item ID",
                        description = "Enter the item ID of what you'd like to alch, you can find the IDs here: https://www.osrsbox.com/tools/item-search/",
                        defaultValue = "0",
                        optionType = OptionType.INTEGER
                ),
                @ScriptConfiguration(
                        name =  "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings?",
                        defaultValue = "1",
                        optionType = OptionType.WORLDHOPPER
                )
        }
)

public class PublicAlcher extends AbstractScript {
    public static int itemID;
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;

    @Override
    public void onStart(){
        Logger.log("Setting everything up");

        Map<String, String> configs = getConfigurations();
        itemID = Integer.parseInt(configs.get("Item ID"));
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));

        Logger.log("Checking your inventory for required items");
        boolean hasNats = Inventory.contains(ItemList.NATURE_RUNE_561, 0.60);
        boolean hasAlchItem = Inventory.contains(itemID, 0.60);

        if (!hasNats || !hasAlchItem) {
            Logger.log("You dont have the required items in inventory");
            Script.stop();
        }
    }

    List<Task> alchTasks = Arrays.asList(
            new CheckForItems(),
            new PerformAlching()
    );

    @Override
    public void poll() {
        // Check if we should WH
        if (hopEnabled) {
            Game.hop(hopProfile, useWDH, false); // Check if we should worldhop
        }

        //Run tasks
        for (Task task : alchTasks) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }
}
