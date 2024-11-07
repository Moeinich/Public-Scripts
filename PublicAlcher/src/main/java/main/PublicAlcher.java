package main;

import helpers.*;
import helpers.annotations.AllowedValue;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.OptionType;
import helpers.utils.Skills;
import tasks.CheckForItems;
import tasks.PerformAlching;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;


@ScriptManifest(
        name = "Public Alcher",
        description = "An easy to use Alcher. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.19",
        guideLink = "",
        categories = {ScriptCategory.Magic}
)
@ScriptConfiguration.List(
        value = {
                @ScriptConfiguration(
                        name = "Item ID",
                        description = "Enter the item ID of what you'd like to alch, you can find the IDs here: https://www.osrsbox.com/tools/item-search/",
                        defaultValue = "0",
                        minMaxIntValues = {0, 30000},
                        optionType = OptionType.INTEGER
                ),
                @ScriptConfiguration(
                        name =  "Alchemy spell",
                        description = "Which alchemy spell would you like to cast?",
                        defaultValue = "High Level Alchemy",
                        allowedValues = {
                                @AllowedValue(optionName = "High Level Alchemy"),
                                @AllowedValue(optionName = "Low Level Alchemy")
                        },
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name = "Use world hopper?",
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
    public static int magicLevel = 0;
    public static String alchemySpell;

    @Override
    public void onStart(){
        Logger.log("Starting Public Alcher v1.16");

        Map<String, String> configs = getConfigurations();
        itemID = Integer.parseInt(configs.get("Item ID"));
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));
        alchemySpell = configs.get("Alchemy spell");

        // Check our levels here to decide which spell to use.
        GameTabs.openStatsTab();
        Condition.sleep(600);
        magicLevel = Stats.getRealLevel(Skills.MAGIC);

        Logger.log("Magic level is: " + magicLevel);

        // Open the inventory again.
        GameTabs.openInventoryTab();
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
