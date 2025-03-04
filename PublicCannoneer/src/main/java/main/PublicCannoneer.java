package main;

import helpers.*;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.OptionType;
import helpers.utils.UITabs;
import tasks.BreakManager;
import tasks.CheckForItems;
import tasks.PerformCannoning;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static helpers.Interfaces.*;


@ScriptManifest(
        name = "Public Cannoneer",
        description = "An easy to use AFK cannon script. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.18",
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
                ),
                @ScriptConfiguration(
                        name = "Break every",
                        description = "Break every random x minutes",
                        defaultValue = "30",
                        minMaxIntValues = {15, 55},
                        optionType = OptionType.INTEGER_SLIDER
                )
        }
)

public class PublicCannoneer extends AbstractScript {
    public static int waitTime;
    public static int breakEvery;

    @Override
    public void onStart(){
        Logger.log("Setting everything up");
        Map<String, String> configs = getConfigurations();
        waitTime = Integer.parseInt(configs.get("Max wait time"));
        breakEvery = Integer.parseInt(configs.get("Break every"));

        // Disable break/afk handlers to use its own built-in
        Client.disableBreakHandler();
        Client.disableAFKHandler();
    }

    // Task list!
    List<Task> cannonTasks = Arrays.asList(
            new BreakManager(),
            new CheckForItems(),
            new PerformCannoning()
    );

    @Override
    public void poll() {
        if (!GameTabs.isTabOpen(UITabs.INVENTORY)) {
            GameTabs.openTab(UITabs.INVENTORY);
            Condition.wait(() -> GameTabs.isTabOpen(UITabs.INVENTORY), 50, 10);
        }

        //Run tasks
        for (Task task : cannonTasks) {
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
