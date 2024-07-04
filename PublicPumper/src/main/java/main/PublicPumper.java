package main;

import helpers.*;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.OptionType;
import tasks.PerformPumping;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;


@ScriptManifest(
        name = "Public Pumper",
        description = "An easy to use AFK BF Pumper. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.01",
        guideLink = "",
        categories = {ScriptCategory.Combat}
)
@ScriptConfiguration.List(
        value = {
                @ScriptConfiguration(
                        name =  "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings? The script will only worldhop during mining",
                        defaultValue = "true",
                        optionType = OptionType.WORLDHOPPER
                )
        }
)

public class PublicPumper extends AbstractScript {
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;

    @Override
    public void onStart(){
        Logger.log("Setting everything up");
        Map<String, String> configs = getConfigurations();
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));
    }

    // Task list!
    List<Task> cannonTasks = Arrays.asList(
            new PerformPumping()
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
