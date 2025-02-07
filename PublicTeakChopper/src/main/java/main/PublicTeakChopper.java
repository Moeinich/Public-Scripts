package main;

import helpers.*;
import helpers.annotations.AllowedValue;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.MapChunk;
import helpers.utils.OptionType;
import tasks.CheckEquipment;
import tasks.DropLogs;
import tasks.PerformChopping;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static helpers.Interfaces.*;

@ScriptManifest(
        name = "Public Teak Chopper",
        description = "An easy to use Teak Chopper. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.15",
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
                        name = "Amount of safe slots",
                        description = "this will exclude x slots starting from the bottom of inventory and moving up.",
                        defaultValue = "0",
                        minMaxIntValues = {0, 28},
                        optionType = OptionType.INTEGER_SLIDER
                ),
                @ScriptConfiguration(
                        name = "Randomize dropping",
                        description = "Select this option if you would like to randomize drop patterns",
                        defaultValue = "false",
                        optionType = OptionType.BOOLEAN
                ),
                @ScriptConfiguration(
                        name =  "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings?",
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
    public static Boolean axeEquipped = false;
    public static Boolean randomDropping;
    public static int slotsToSafeConfig = 0;

    @Override
    public void onStart(){
        Logger.log("Setting everything up");

        Map<String, String> configs = getConfigurations();
        Location = configs.get("Location");
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));
        slotsToSafeConfig = Integer.parseInt(configs.get("Amount of safe slots"));
        randomDropping = Boolean.valueOf(configs.get("Randomize dropping"));

        Logger.log("Use worldhops? " + useWDH);

        Walker.setup(new MapChunk(new String[]{"33-46", "35-44"}, "0"));
    }

    // Task list!
    List<Task> chopTasks = Arrays.asList(
            new CheckEquipment(),
            new DropLogs(),
            new PerformChopping()
    );

    @Override
    public void poll() {
        if (useWDH) {
            Game.hop(hopProfile, useWDH, false); // Check if we should worldhop
        }

        //Run tasks
        for (Task task : chopTasks) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }

    static Random random = new Random();
    public static int generateRandomDelay(int lowerBound, int upperBound) {
        // Swap if lowerBound is greater than upperBound
        if (lowerBound > upperBound) {
            int temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }
        return lowerBound + random.nextInt(upperBound - lowerBound + 1);
    }
}
