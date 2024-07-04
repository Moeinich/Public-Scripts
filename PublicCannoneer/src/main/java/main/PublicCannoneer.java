package main;

import helpers.*;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.OptionType;
import tasks.CheckForItems;
import tasks.PerformCannoning;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;


@ScriptManifest(
        name = "Public Cannoneer",
        description = "An easy to use AFK cannon script. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.01",
        guideLink = "https://wiki.mufasaclient.com/docs/public-cannoneer/",
        categories = {ScriptCategory.Combat}
)
@ScriptConfiguration.List(
        value = {
                @ScriptConfiguration(
                        name = "Max wait time",
                        description = "Max wait time between re-fills",
                        defaultValue = "31",
                        minMaxIntValues = {15, 40},
                        optionType = OptionType.INTEGER_SLIDER
                )
        }
)

public class PublicCannoneer extends AbstractScript {
    public static int waitTime;
    @Override
    public void onStart(){
        Logger.log("Setting everything up");
        Map<String, String> configs = getConfigurations();
        waitTime = Integer.parseInt(configs.get("Max wait time"));
    }

    // Task list!
    List<Task> cannonTasks = Arrays.asList(
            new CheckForItems(),
            new PerformCannoning()
    );

    @Override
    public void poll() {
        //Run tasks
        for (Task task : cannonTasks) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }
}
