package main;

import helpers.*;
import helpers.annotations.AllowedValue;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.OptionType;
import tasks.Bank;
import tasks.Eat;
import tasks.HandleInventory;
import tasks.DoPickpockets;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static helpers.Interfaces.*;


@ScriptManifest(
        name = "Public Master Farmer",
        description = "An easy to use Master Farmer snatcher. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.20",
        guideLink = "",
        skipZoomSetup = true,
        categories = {ScriptCategory.Magic}
)
@ScriptConfiguration.List(
        value = {
                @ScriptConfiguration(
                        name = "Amount of safe slots",
                        description = "this will exclude x slots starting from the bottom of inventory and moving up.",
                        defaultValue = "20",
                        minMaxIntValues = {0, 28},
                        optionType = OptionType.INTEGER_SLIDER
                ),
                @ScriptConfiguration(
                        name =  "BankTab",
                        description = "What bank tab is your food located in?",
                        defaultValue = "0",
                        optionType = OptionType.BANKTABS
                ),
                @ScriptConfiguration(
                        name = "Food",
                        description = "Select which food to use",
                        defaultValue = "Shark",
                        allowedValues = {
                                @AllowedValue(optionName = "None"),
                                @AllowedValue(optionIcon = "1891", optionName = "Cakes"),
                                @AllowedValue(optionIcon = "379", optionName = "Lobster"),
                                @AllowedValue(optionIcon = "373", optionName = "Swordfish"),
                                @AllowedValue(optionIcon = "385", optionName = "Shark"),
                                @AllowedValue(optionIcon = "359", optionName = "Tuna"),
                                @AllowedValue(optionIcon = "333", optionName = "Trout"),
                                @AllowedValue(optionIcon = "329", optionName = "Salmon"),
                                @AllowedValue(optionIcon = "365", optionName = "Bass"),
                                @AllowedValue(optionIcon = "3144", optionName = "Cooked karambwan"),
                                @AllowedValue(optionIcon = "391", optionName = "Manta ray"),
                                @AllowedValue(optionIcon = "13441", optionName = "Anglerfish")
                        },
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name = "HP to eat at",
                        description = "Select the HP amount you'd like to eat at",
                        defaultValue = "20",
                        minMaxIntValues = {0, 100},
                        optionType = OptionType.INTEGER_SLIDER
                ),
                @ScriptConfiguration(
                        name = "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings?",
                        defaultValue = "1",
                        optionType = OptionType.WORLDHOPPER
                )
        }
)

public class PublicMasterFarmer extends AbstractScript {
    private static final Random random = new Random();
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;
    public static int foodID;

    public static int hpToEat;
    public static int currentHP;
    public static String selectedFood;
    public static int selectedBankTab;

    public static int slotsToSafeConfig = 0;

    @Override
    public void onStart(){
        Logger.log("Starting Public Alcher v1.16");

        Map<String, String> configs = getConfigurations();
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));

        selectedBankTab = Integer.parseInt(configs.get("BankTab")); // Get the bankTab value from the last configuration option
        hpToEat = Integer.parseInt(configs.get("HP to eat at"));
        selectedFood = configs.get("Food");

        slotsToSafeConfig = Integer.parseInt(configs.get("Amount of safe slots"));

        setupFoodIDs();

        Game.setZoom("2");

        // Open the inventory again.
        GameTabs.openInventoryTab();
    }

    List<Task> pickpocketTasks = Arrays.asList(
            new Eat(),
            new Bank(),
            new HandleInventory(),
            new DoPickpockets()
    );

    @Override
    public void poll() {
        // Check if we should WH
        if (hopEnabled) {
            Game.hop(hopProfile, useWDH, false);
        }

        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 10);
        }

        currentHP = Player.getHP();

        //Run tasks
        for (Task task : pickpocketTasks) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }

    private void setupFoodIDs() {
        Logger.debugLog("Setting up food IDs");
        switch (selectedFood) {
            case "None":
                foodID = 0;
                break;
            case "Cakes":
                foodID = 1891;
                break;
            case "Lobster":
                foodID = 379;
                break;
            case "Swordfish":
                foodID = 373;
                break;
            case "Shark":
                foodID = 385;
                break;
            case "Tuna":
                foodID = 359;
                break;
            case "Trout":
                foodID = 333;
                break;
            case "Salmon":
                foodID = 329;
                break;
            case "Bass":
                foodID = 365;
                break;
            case "Cooked karambwan":
                foodID = 3144;
                break;
            case "Manta ray":
                foodID = 391;
                break;
            case "Anglerfish":
                foodID = 13441;
                break;
            default:
                Logger.log("Invalid food configuration, please restart script");
                Script.stop();
                break;
        }
    }

    public static int getRandomInt(int lowerBound, int upperBound) {
        // Swap if lowerBound is greater than upperBound
        if (lowerBound > upperBound) {
            int temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }
        return lowerBound + random.nextInt(upperBound - lowerBound + 1);
    }
}
