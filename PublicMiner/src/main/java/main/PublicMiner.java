package main;

import helpers.*;
import helpers.annotations.AllowedValue;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.MapChunk;
import helpers.utils.OptionType;
import tasks.*;
import utils.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static helpers.Interfaces.*;

@ScriptManifest(
        name = "Public Miner",
        description = "Mines ores in different places. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.247",
        guideLink = "https://wiki.mufasaclient.com/docs/publicminer/",
        categories = {ScriptCategory.Mining}
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration( // Location config
                        name =  "Location",
                        description = "Which location would you like to use? be sure to read the script guide for which ores are supported in specific locations",
                        defaultValue = "Isle of Souls",
                        allowedValues = {
                                @AllowedValue(optionName = "Varrock East"),
                                @AllowedValue(optionName = "Varrock West"),
                                @AllowedValue(optionName = "Isle of Souls"),
                                @AllowedValue(optionName = "Al Kharid East"),
                                @AllowedValue(optionName = "Mining Guild - Iron East"),
                                @AllowedValue(optionName = "Mining Guild - Iron West"),
                                @AllowedValue(optionName = "Mining Guild - Coal")
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
                                @AllowedValue(optionIcon = "453", optionName = "Coal"),

                        },
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration( // Bank config boolean
                        name =  "Bank everything",
                        description = "Make sure you read the script guide if banking is supported in your location!",
                        defaultValue = "false",
                        optionType = OptionType.BOOLEAN
                ),
                @ScriptConfiguration( // Worldhopper config
                        name =  "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings? The script will only worldhop when other players are nearby",
                        defaultValue = "true",
                        optionType = OptionType.WORLDHOPPER
                ),
        }
)

public class PublicMiner extends AbstractScript {
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
    public static Boolean pickaxeEquipped = false;
    public static int miningLevel = 99; //Just setting it to 99 atm to pass checks ;)
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;
    public static LocationInfo locationInfo;
    public static RegionInfo regionInfo;
    public static VeinColors veinColors;
    public static PathsToBanks pathsToBanks;
    public static int slotsToSafeConfig = 0;

    @Override
    public void onStart(){
        //Setup configs
        Map<String, String> configs = getConfigurations();
        Location = configs.get("Location");
        oreType = configs.get("Ore type");
        bankOres = Boolean.valueOf((configs.get("Bank everything")));
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));
        slotsToSafeConfig = Integer.parseInt(configs.get("Amount of safe slots"));

        //Setup enum values
        setupRegionInfo();
        setupLocationInfo();
        setupVeinColors();
        setupPathsToBank();
        setupOreTypeInts();

        // 47-151, 46-151, 46-152, 47-152, 49-52, 49-53, 49-54, 50-54, 50-53, 51-53, 51-52, 53-49, 53-50, 52-49, 51-49, 50-49, 34-44, 34-43, 33-43, 33-44
        MapChunk mapChunk = new MapChunk(new String[]
                {
                        // Mining Guild
                        "47-151", "46-151", "46-152", "47-152",
                        //Varrock
                        "49-52", "49-53", "49-54", "50-54", "50-53", "51-53", "51-52",
                        //Al Kharid
                        "53-49", "53-50", "52-49", "51-49", "50-49",
                        //Isle of souls
                        "34-44", "34-43", "33-43", "33-44"
                },
                "0");

        Walker.setup(mapChunk);
    }

    @Override
    public void poll() {
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
            case "Isle of Souls":
                regionInfo = RegionInfo.ISLE_OF_SOULS;
                break;
            case "Al Kharid East":
                regionInfo = RegionInfo.AL_KHARID_EAST;
                break;
            case "Mining Guild - Iron East":
                regionInfo = RegionInfo.MINING_GUILD_IRON_EAST;
                break;
            case "Mining Guild - Iron West":
                regionInfo = RegionInfo.MINING_GUILD_IRON_WEST;
                break;
            case "Mining Guild - Coal":
                regionInfo = RegionInfo.MINING_GUILD_COAL;
                break;
            default:
                Logger.log("Incorrect script setup, please read script guide!");
                break;
        }
    }


    private void setupLocationInfo() {
        Logger.debugLog("Setting up location info");
        if (regionInfo.equals(RegionInfo.MINING_GUILD_IRON_EAST)) {
            locationInfo = LocationInfo.MINING_GUILD_IRON_EAST;
            return;
        }

        if (regionInfo.equals(RegionInfo.MINING_GUILD_IRON_WEST)) {
            locationInfo = LocationInfo.MINING_GUILD_IRON_WEST;
            return;
        }

        if (regionInfo.equals(RegionInfo.MINING_GUILD_COAL)) {
            locationInfo = LocationInfo.MINING_GUILD_COAL;
            return;
        }

        if (regionInfo.equals(RegionInfo.VARROCK_EAST)) {
            switch (oreType) {
                case "Copper ore":
                    locationInfo = LocationInfo.VARROCK_EAST_COPPER;
                    break;
                case "Iron ore":
                    locationInfo = LocationInfo.VARROCK_EAST_IRON;
                    break;
                case "Tin ore":
                    locationInfo = LocationInfo.VARROCK_EAST_TIN;
                    break;
                default:
                    Logger.log("Incorrect setup configuration");
                    Script.stop();
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
                    Script.stop();
                    break;
            }
        } else if (regionInfo.equals(RegionInfo.ISLE_OF_SOULS)) {
            locationInfo = LocationInfo.ISLE_OF_SOULS;
        } else if (regionInfo.equals(RegionInfo.AL_KHARID_EAST)) {
            locationInfo = LocationInfo.AL_KHARID_EAST;
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
            case "Coal":
                veinColors = VeinColors.COAL;
                break;
            default:
                Logger.debugLog("Incorrect setup for vein colors.");
                Script.stop();
        }
    }

    private void setupPathsToBank() {
        Logger.debugLog("Setting up bank pathing");
        switch (Location) {
            case "Varrock East":
                pathsToBanks = PathsToBanks.VARROCK_EAST;
                break;
            case "Varrock West":
                pathsToBanks = PathsToBanks.VARROCK_WEST;
                break;
            case "Isle of Souls":
                pathsToBanks = PathsToBanks.ISLE_OF_SOULS;
                break;
            case "Al Kharid East":
                pathsToBanks = null;
                break;
            case "Mining Guild - Iron East":
                pathsToBanks = PathsToBanks.MINING_GUILD_IRON_EAST;
                break;
            case "Mining Guild - Iron West":
                pathsToBanks = PathsToBanks.MINING_GUILD_IRON_WEST;
                break;
            case "Mining Guild - Coal":
                pathsToBanks = PathsToBanks.MINING_GUILD_COAL;
                break;
            default:
                Logger.log("Incorrect script setup, please read the script guide!");
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
            case "Coal":
                oreTypeInt = 453;
                if (miningLevel < 30) {
                    Logger.log("You dont have the required mining level for coal");
                    Script.stop();
                }
                break;
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