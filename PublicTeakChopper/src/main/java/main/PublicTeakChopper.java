package main;

import helpers.*;
import helpers.annotations.AllowedValue;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.OptionType;
import tasks.CheckEquipment;
import tasks.DropLogs;
import tasks.PerformChopping;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;

@ScriptManifest(
        name = "Public Teak Chopper",
        description = "An easy to use Teak Chopper. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.0",
        guideLink = "https://wiki.mufasaclient.com/docs/public-teak-chopper/",
        categories = {ScriptCategory.Woodcutting}
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration( // Location config
                        name =  "Location",
                        description = "Which location would you like to use?",
                        defaultValue = "Isle of Souls",
                        allowedValues = {
                                @AllowedValue(optionName = "Isle of Souls")
                        },
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name =  "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings? The script will only worldhop during mining",
                        defaultValue = "true",
                        optionType = OptionType.WORLDHOPPER
                )
        }
)

public class PublicTeakChopper extends AbstractScript {
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;
    public static String Location;

    @Override
    public void onStart(){
        Logger.log("Setting everything up");

        Map<String, String> configs = getConfigurations();
        Location = configs.get("Location");
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));
    }

    // Task list!
    List<Task> chopTasks = Arrays.asList(
            new CheckEquipment(),
            new DropLogs(),
            new PerformChopping()
    );

    @Override
    public void poll() {
        //Run tasks
        for (Task task : chopTasks) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }
}
