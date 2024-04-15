package main;

import helpers.*;
import helpers.utils.ItemList;
import helpers.utils.OptionType;
import tasks.Bank;
import tasks.CheckPickaxe;
import tasks.DropOres;
import tasks.performMining;
import utils.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;

@ScriptManifest(
        name = "AIO Miner",
        description = "Mines ores in different places. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "0.1",
        guideLink = "https://wiki.mufasaclient.com/docs/aiominer/",
        categories = {ScriptCategory.Mining}
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration( // Location config
                        name =  "Location",
                        description = "Which location would you like to use? be sure to read the script guide for which ores are supported in specific locations",
                        defaultValue = "Varrock East",
                        allowedValues = {
                                @AllowedValue(optionName = "Varrock East"),
                                @AllowedValue(optionName = "Varrock West"),
                                @AllowedValue(optionName = "Soul Isles"),
                                //@AllowedValue(optionName = "Al kharid East"),
                                //@AllowedValue(optionName = "Mining Guild"),
                        },
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration( // Ore type
                        name =  "Ore type",
                        description = "Which ore would you like to mine?",
                        defaultValue = "Iron ore",
                        allowedValues = {
                                @AllowedValue(optionIcon = "436", optionName = "Copper ore"),
                                @AllowedValue(optionIcon = "438", optionName = "Tin ore"),
                                @AllowedValue(optionIcon = "434", optionName = "Clay"),
                                @AllowedValue(optionIcon = "442", optionName = "Silver ore"),
                                @AllowedValue(optionIcon = "440", optionName = "Iron ore"),
                                //@AllowedValue(optionIcon = "453", optionName = "Coal"),

                        },
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration( // Bank config boolean
                        name =  "Bank ores",
                        description = "Make sure you read the script guide if banking is supported in your location!",
                        defaultValue = "false",
                        optionType = OptionType.BOOLEAN
                ),
                @ScriptConfiguration(
                        name = "Drop clues",
                        description = "Toggle this if you would like to drop clues",
                        defaultValue = "true",
                        optionType =  OptionType.BOOLEAN
                ),
                @ScriptConfiguration( // Worldhopper config
                        name =  "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings? The script will only worldhop during mining",
                        defaultValue = "false",
                        optionType = OptionType.WORLDHOPPER
                ),
        }
)

public class AIOMiner extends AbstractScript {
    List<Task> miningTasks = Arrays.asList(
            new CheckPickaxe(),
            new Bank(),
            new DropOres(),
            new performMining()
    );

    public static String Location;
    public static String oreType;
    public static int oreTypeInt;
    public static Boolean bankOres;
    public static int miningLevel = 99; //Just setting it to 99 atm to pass checks ;)
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;
    public static Boolean dropClues;

    public static LocationInfo locationInfo;
    public static RegionInfo regionInfo;
    public static VeinColors veinColors;
    public static PathsToBanks pathsToBanks;

    public static int[] clueIDs = {
            ItemList.CLUE_GEODE_BEGINNER_23442,
            ItemList.CLUE_GEODE_EASY_20358,
            ItemList.CLUE_GEODE_MEDIUM_20360,
            ItemList.CLUE_GEODE_HARD_20362,
            ItemList.CLUE_GEODE_ELITE_20364
    };

    @Override
    public void onStart(){
        //Setup configs
        Map<String, String> configs = getConfigurations();
        Location = configs.get("Location");
        oreType = configs.get("Ore type");
        bankOres = Boolean.valueOf((configs.get("Bank ores")));
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));
        dropClues = Boolean.valueOf(configs.get("Drop clues"));

        //Check and cache STARTING mining level (just to make sure people dont fuck up)
//        if (!GameTabs.isStatsTabOpen()) {
//            GameTabs.openStatsTab();
//        }
//        if (GameTabs.isStatsTabOpen()) {
//            miningLevel = Stats.getRealLevel("Mining");
//        }

        //Setup enum values
        setupRegionInfo();
        setupLocationInfo();
        setupVeinColors();
        setupPathsToBank();
        setupOreTypeInts();
    }

    @Override
    public void poll() {
        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
        }

        //Run tasks
            for (Task task : miningTasks) {
                if (task.activate()) {
                    task.execute();
                    return;
                }
            }
        }

    private void setupRegionInfo() {
        Logger.debugLog("Setting up region info");
        switch (Location) {
            case "Varrock East":
                regionInfo = RegionInfo.VARROCK_EAST;
                break;
            case "Varrock West":
                regionInfo = RegionInfo.VARROCK_WEST;
                break;
            case "Soul Isles":
                regionInfo = RegionInfo.SOUL_ISLES;
                break;
        }
    }

    private void setupLocationInfo() {
        Logger.debugLog("Setting up location info");
        if (regionInfo.equals(RegionInfo.VARROCK_EAST)) {
            switch (oreType) {
                case "Copper ore":
                    locationInfo = LocationInfo.VARROCK_EAST_COPPER;
                    break;
                case "Iron ore":
                    locationInfo = LocationInfo.VARROCK_EAST_IRON;
                    break;
                default:
                    Logger.log("Incorrect setup configuration");
                    break;
            }
        } else if (regionInfo.equals(RegionInfo.VARROCK_WEST)) {
            switch (oreType) {
                case "Clay":
                    locationInfo = LocationInfo.VARROCK_WEST_CLAY;
                    break;
                case "Silver ore":
                    locationInfo = LocationInfo.VARROCK_WEST_SILVER;
                    break;
                case "Iron ore":
                    locationInfo = LocationInfo.VARROCK_WEST_IRON;
                    break;
                default:
                    Logger.log("Incorrect setup configuration");
                    break;
            }
        } else if (regionInfo.equals(RegionInfo.SOUL_ISLES)) {
            locationInfo = LocationInfo.SOUL_ISLES;
        }
    }

    private void setupVeinColors() {
        Logger.debugLog("Setting up vein info");
        switch (oreType) {
            case "Copper ore":
                veinColors = VeinColors.COPPER_VEIN;
                break;
            case "Tin ore":
                veinColors = VeinColors.TIN_VEIN;
                break;
            case "Iron ore":
                veinColors = VeinColors.IRON_VEIN;
                break;
            case "Clay":
                veinColors = VeinColors.CLAY;
                break;
            case "Silver ore":
                veinColors = VeinColors.SILVER;
                break;
        }
    }

    private void setupPathsToBank() {
        Logger.debugLog("Setting up bank pathing");
        switch (Location) {
            case "Varrock East":
                pathsToBanks = PathsToBanks.VARROCK_EAST_BANKPATHS;
                break;
            case "Varrock West":
                pathsToBanks = PathsToBanks.VARROCK_WEST_BANKPATHS;
                break;
            case "Soul Isles":
                pathsToBanks = PathsToBanks.SOUL_ISLES;
                break;
        }
    }

    private void setupOreTypeInts() {
        Logger.debugLog("Setting up ore type info");
        switch (oreType) {
            case "Copper ore":
                oreTypeInt = 436;
                break;
            case "Tin ore":
                oreTypeInt = 438;
                break;
            case "Clay":
                oreTypeInt = 434;
                break;
            case "Silver ore":
                oreTypeInt = 442;
                if (miningLevel < 20) {
                    Logger.log("You dont have the required mining level for silver");
                    Script.stop();
                }
                break;
            case "Iron ore":
                oreTypeInt = 440;
                if (miningLevel < 15) {
                    Logger.log("You dont have the required mining level for iron");
                    Script.stop();
                }
                break;
        }
    }
}