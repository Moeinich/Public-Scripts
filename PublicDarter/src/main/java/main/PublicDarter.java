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
        version = "1.0",
        guideLink = "",
        categories = {ScriptCategory.Mining}
)
@ScriptConfiguration.List(
        {
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

    @Override
    public void onStart() {
        Logger.log("Setting everything up");

        Map<String, String> configs = getConfigurations();
        hopProfile = configs.get("Use world hopper?");
        hopEnabled = Boolean.parseBoolean(configs.get("Use world hopper?.enabled"));
        useWDH = Boolean.parseBoolean(configs.get("Use world hopper?.useWDH"));
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
