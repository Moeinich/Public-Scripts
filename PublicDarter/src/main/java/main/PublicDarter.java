package main;

import helpers.AbstractScript;
import helpers.ScriptCategory;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.*;
import tasks.CheckInventory;
import tasks.CreateDarts;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;

@ScriptManifest(
        name = "Public Darter",
        description = "An easy to use Dart crafter. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.03",
        guideLink = "https://wiki.mufasaclient.com/docs/publicdarter/",
        categories = {ScriptCategory.Fletching}
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "Min tap speed",
                        description = "Set the minimum tap speed you would like to use",
                        defaultValue = "100",
                        minMaxIntValues = {50, 500},
                        optionType = OptionType.INTEGER_SLIDER
                ),
                @ScriptConfiguration(
                        name = "Max tap speed",
                        description = "Set the maximum tap speed you would like to use",
                        defaultValue = "400",
                        minMaxIntValues = {50, 500},
                        optionType = OptionType.INTEGER_SLIDER
                ),
                @ScriptConfiguration(
                        name = "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings?",
                        defaultValue = "true",
                        optionType = OptionType.WORLDHOPPER
                )
        }
)

public class PublicDarter extends AbstractScript {
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;
    public static int minTapSpeed;
    public static int maxTapSpeed;

    @Override
    public void onStart() {
        Logger.log("Setting everything up");

        Map<String, String> configs = getConfigurations();
        hopProfile = configs.get("Use world hopper?");
        hopEnabled = Boolean.parseBoolean(configs.get("Use world hopper?.enabled"));
        useWDH = Boolean.parseBoolean(configs.get("Use world hopper?.useWDH"));
        minTapSpeed = Integer.parseInt(configs.get("Min tap speed"));
        maxTapSpeed = Integer.parseInt(configs.get("Max tap speed"));
    }

    // Task list!
    List<Task> dartTasks = Arrays.asList(
            new CheckInventory(),
            new CreateDarts()
    );

    @Override
    public void poll() {
        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 20);
        }

        //Run tasks
        for (Task task : dartTasks) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }
}
