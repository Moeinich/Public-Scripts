package main;

import helpers.*;
import helpers.annotations.AllowedValue;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.ItemList;
import helpers.utils.ItemPair;
import helpers.utils.MapChunk;
import helpers.utils.OptionType;
import tasks.Bank;
import tasks.Eat;
import tasks.Drop;
import tasks.DoPickpockets;
import utils.Task;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static helpers.Interfaces.*;


@ScriptManifest(
        name = "Public Master Farmer",
        description = "An easy to use Master Farmer thiever. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.0",
        guideLink = "",
        skipZoomSetup = true,
        categories = {ScriptCategory.Thieving}
)
@ScriptConfiguration.List(
        value = {
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
                        name =  "Select seeds",
                        description = "Select which seeds you would like to keep. Please remember, we cannot differentiate herb seeds.",
                        defaultValue = "Barley seed, Jute seed, Limpwurt seed, Seaweed spore, Guam seed, Marrentill seed, Tarromin seed, Harralander seed, Ranarr seed, Toadflax seed, Irit seed, Avantoe seed, Kwuarm seed, Snapdragon seed, Cadantine seed, Lantadyme seed, Dwarf Weed seed, Torstol seed",
                        allowedValues = {
                                // Allotments
                                @AllowedValue(optionName = "Potato seed", optionIcon = "5318"),
                                @AllowedValue(optionName = "Onion seed", optionIcon = "7550"),
                                @AllowedValue(optionName = "Cabbage seed", optionIcon = "5324"),
                                @AllowedValue(optionName = "Tomato seed", optionIcon = "13800"),
                                @AllowedValue(optionName = "Sweetcorn seed", optionIcon = "5320"),
                                @AllowedValue(optionName = "Strawberry seed", optionIcon = "5323"),
                                @AllowedValue(optionName = "Watermelon seed", optionIcon = "5321"),
                                @AllowedValue(optionName = "Snape grass seed", optionIcon = "22879"),

                                // Hops
                                @AllowedValue(optionName = "Barley seed", optionIcon = "5305"),
                                @AllowedValue(optionName = "Hammerstone seed", optionIcon = "5307"),
                                @AllowedValue(optionName = "Asgarnian seed", optionIcon = "5308"),
                                @AllowedValue(optionName = "Jute seed", optionIcon = "5306"),
                                @AllowedValue(optionName = "Yanillian seed", optionIcon = "5309"),
                                @AllowedValue(optionName = "Krandorian seed", optionIcon = "5310"),
                                @AllowedValue(optionName = "Wildblood seed", optionIcon = "5311"),

                                // Flowers
                                @AllowedValue(optionName = "Marigold seed", optionIcon = "5096"),
                                @AllowedValue(optionName = "Nasturtium seed", optionIcon = "5098"),
                                @AllowedValue(optionName = "Rosemary seed", optionIcon = "5097"),
                                @AllowedValue(optionName = "Woad seed", optionIcon = "5099"),
                                @AllowedValue(optionName = "Limpwurt seed", optionIcon = "5100"),

                                // Bushes
                                @AllowedValue(optionName = "Redberry seed", optionIcon = "5101"),
                                @AllowedValue(optionName = "Cadavaberry seed", optionIcon = "5102"),
                                @AllowedValue(optionName = "Dwellberry seed", optionIcon = "5103"),
                                @AllowedValue(optionName = "Jangerberry seed", optionIcon = "5104"),
                                @AllowedValue(optionName = "Whiteberry seed", optionIcon = "5105"),
                                @AllowedValue(optionName = "Poison Ivy seed", optionIcon = "5106"),

                                // Special
                                @AllowedValue(optionName = "Mushroom spore", optionIcon = "5282"),
                                @AllowedValue(optionName = "Belladonna seed", optionIcon = "5281"),
                                @AllowedValue(optionName = "Cactus seed", optionIcon = "5280"),
                                @AllowedValue(optionName = "Seaweed spore", optionIcon = "21490"),
                                @AllowedValue(optionName = "Potato cactus", optionIcon = "3138"),

                                // Herbs
                                @AllowedValue(optionName = "Guam seed", optionIcon = "5291"),
                                @AllowedValue(optionName = "Marrentill seed", optionIcon = "5292"),
                                @AllowedValue(optionName = "Tarromin seed", optionIcon = "5293"),
                                @AllowedValue(optionName = "Harralander seed", optionIcon = "5294"),
                                @AllowedValue(optionName = "Ranarr seed", optionIcon = "5295"),
                                @AllowedValue(optionName = "Toadflax seed", optionIcon = "5296"),
                                @AllowedValue(optionName = "Irit seed", optionIcon = "5297"),
                                @AllowedValue(optionName = "Avantoe seed", optionIcon = "5298"),
                                @AllowedValue(optionName = "Kwuarm seed", optionIcon = "5299"),
                                @AllowedValue(optionName = "Snapdragon seed", optionIcon = "5300"),
                                @AllowedValue(optionName = "Cadantine seed", optionIcon = "5301"),
                                @AllowedValue(optionName = "Lantadyme seed", optionIcon = "5302"),
                                @AllowedValue(optionName = "Dwarf Weed seed", optionIcon = "5303"),
                                @AllowedValue(optionName = "Torstol seed", optionIcon = "5304"),
                        },
                        optionType = OptionType.MULTI_SELECTION
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
    private static String selectedSeeds;

    public static int hpToEat;
    public static int currentHP;
    public static String selectedFood;
    public static int selectedBankTab;

    @Override
    public void onStart(){
        Logger.log("Starting PublicMasterFarmer");

        Map<String, String> configs = getConfigurations();
        hopProfile = (configs.get("Use world hopper?"));
        hopEnabled = Boolean.valueOf((configs.get("Use world hopper?.enabled")));
        useWDH = Boolean.valueOf((configs.get("Use world hopper?.useWDH")));
        selectedSeeds = configs.get("Select seeds");

        selectedBankTab = Integer.parseInt(configs.get("BankTab")); // Get the bankTab value from the last configuration option
        hpToEat = Integer.parseInt(configs.get("HP to eat at"));
        selectedFood = configs.get("Food");

        // 24-48, 27-47
        MapChunk mapChunk = new MapChunk(new String[]
                {
                        // Varlamore
                        "24-48", "27-47"
                },
                "0");

        Walker.setup(mapChunk);

        setupFoodIDs();
        buildLists();

        Game.setZoom("2");

        // Open the inventory again.
        GameTabs.openInventoryTab();
    }

    List<Task> pickpocketTasks = Arrays.asList(
            new Eat(),
            new Bank(),
            new Drop(),
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

    public static final List<ItemPair> fullList = Arrays.asList(
            // Allotments
            new ItemPair(ItemList.POTATO_SEED_5318, Color.decode("#544B1D")),
            new ItemPair(ItemList.ONION_SEED_7550, Color.decode("#998756")),
            new ItemPair(ItemList.CABBAGE_SEED_5324, Color.decode("#6A2C1B")),
            new ItemPair(ItemList.TOMATO_SEED_13800, Color.decode("#D6A665")),
            new ItemPair(ItemList.SWEETCORN_SEED_5320, Color.decode("#D8A637")),
            new ItemPair(ItemList.STRAWBERRY_SEED_5323, Color.decode("#9F8D5A")),
            new ItemPair(ItemList.WATERMELON_SEED_5321, Color.decode("#9F8D5A")),
            new ItemPair(ItemList.SNAPE_GRASS_SEED_22879, Color.decode("#0D844C")),

            // Hops
            new ItemPair(ItemList.BARLEY_SEED_5305, Color.decode("#C4AD85")),
            new ItemPair(ItemList.HAMMERSTONE_SEED_5307, Color.decode("#7C875B")),
            new ItemPair(ItemList.ASGARNIAN_SEED_5308, Color.decode("#818F24")),
            new ItemPair(ItemList.JUTE_SEED_5306, Color.decode("#A27D5C")),
            new ItemPair(ItemList.YANILLIAN_SEED_5309, Color.decode("#A59A15")),
            new ItemPair(ItemList.KRANDORIAN_SEED_5310, Color.decode("#987B55")),
            new ItemPair(ItemList.WILDBLOOD_SEED_5311, Color.decode("#8A5F4F")),

            // Flowers
            new ItemPair(ItemList.MARIGOLD_SEED_5096, Color.decode("#A79371")),
            new ItemPair(ItemList.NASTURTIUM_SEED_5098, Color.decode("#837359")),
            new ItemPair(ItemList.ROSEMARY_SEED_5097, Color.decode("#837359")),
            new ItemPair(ItemList.WOAD_SEED_5099, Color.decode("#938663")),
            new ItemPair(ItemList.LIMPWURT_SEED_5100, Color.decode("#8D7312")),

            // Bushes
            new ItemPair(ItemList.REDBERRY_SEED_5101, Color.decode("#915652")),
            new ItemPair(ItemList.CADAVABERRY_SEED_5102, Color.decode("#8C5F8B")),
            new ItemPair(ItemList.DWELLBERRY_SEED_5103, Color.decode("#8C5F8B")),
            new ItemPair(ItemList.JANGERBERRY_SEED_5104, Color.decode("#9B9D63")),
            new ItemPair(ItemList.WHITEBERRY_SEED_5105, Color.decode("#BEB4B4")),
            new ItemPair(ItemList.POISON_IVY_SEED_5106, Color.decode("#B4A9A8")),

            // Special
            new ItemPair(ItemList.MUSHROOM_SPORE_5282, Color.decode("#B5A57A")),
            new ItemPair(ItemList.BELLADONNA_SEED_5281, Color.decode("#997F9F")),
            new ItemPair(ItemList.CACTUS_SEED_5280, Color.decode("#658348")),
            new ItemPair(ItemList.SEAWEED_SPORE_21490, Color.decode("#92CB9F")),
            new ItemPair(ItemList.POTATO_CACTUS_3138, Color.decode("#65713F")),

            // Herbs
            new ItemPair(ItemList.GUAM_SEED_5291, Color.decode("#385E00")),
            new ItemPair(ItemList.MARRENTILL_SEED_5292, Color.decode("#385E00")),
            new ItemPair(ItemList.TARROMIN_SEED_5293, Color.decode("#385E00")),
            new ItemPair(ItemList.HARRALANDER_SEED_5294, Color.decode("#385E00")),
            new ItemPair(ItemList.RANARR_SEED_5295, Color.decode("#385E00")),
            new ItemPair(ItemList.TOADFLAX_SEED_5296, Color.decode("#385E00")),
            new ItemPair(ItemList.IRIT_SEED_5297, Color.decode("#385E00")),
            new ItemPair(ItemList.AVANTOE_SEED_5298, Color.decode("#385E00")),
            new ItemPair(ItemList.KWUARM_SEED_5299, Color.decode("#385E00")),
            new ItemPair(ItemList.SNAPDRAGON_SEED_5300, Color.decode("#385E00")),
            new ItemPair(ItemList.CADANTINE_SEED_5301, Color.decode("#385E00")),
            new ItemPair(ItemList.LANTADYME_SEED_5302, Color.decode("#385E00")),
            new ItemPair(ItemList.DWARF_WEED_SEED_5303, Color.decode("#385E00")),
            new ItemPair(ItemList.TORSTOL_SEED_5304, Color.decode("#385E00"))
    );

    public static List<ItemPair> dropList;
    public static List<ItemPair> keepList;

    private void buildLists() {
        // Parse the selected seeds into a Set for quick lookup
        Set<String> selectedSeedSet = Arrays.stream(selectedSeeds.split(","))
                .map(String::trim) // Remove any extra spaces
                .collect(Collectors.toSet());

        // Clear the existing lists
        dropList.clear();
        keepList.clear();

        // Iterate through the full list
        for (ItemPair seed : fullList) {
            // Extract the seed name from the ItemList constant name
            String seedName = getSeedName(seed.getItemID());

            if (selectedSeedSet.contains(seedName)) {
                // Add to keepList if the seed is selected
                keepList.add(seed);
            } else {
                // Add to dropList if the seed is not selected
                dropList.add(seed);
            }
        }
    }

    // Helper method to map the ItemList integer ID back to a seed name
    private String getSeedName(int itemId) {
        switch (itemId) {
            // Allotments
            case ItemList.POTATO_SEED_5318:
                return "Potato seed";
            case ItemList.ONION_SEED_7550:
                return "Onion seed";
            case ItemList.CABBAGE_SEED_5324:
                return "Cabbage seed";
            case ItemList.TOMATO_SEED_13800:
                return "Tomato seed";
            case ItemList.SWEETCORN_SEED_5320:
                return "Sweetcorn seed";
            case ItemList.STRAWBERRY_SEED_5323:
                return "Strawberry seed";
            case ItemList.WATERMELON_SEED_5321:
                return "Watermelon seed";
            case ItemList.SNAPE_GRASS_SEED_22879:
                return "Snape grass seed";

            // Hops
            case ItemList.BARLEY_SEED_5305:
                return "Barley seed";
            case ItemList.HAMMERSTONE_SEED_5307:
                return "Hammerstone seed";
            case ItemList.ASGARNIAN_SEED_5308:
                return "Asgarnian seed";
            case ItemList.JUTE_SEED_5306:
                return "Jute seed";
            case ItemList.YANILLIAN_SEED_5309:
                return "Yanillian seed";
            case ItemList.KRANDORIAN_SEED_5310:
                return "Krandorian seed";
            case ItemList.WILDBLOOD_SEED_5311:
                return "Wildblood seed";

            // Flowers
            case ItemList.MARIGOLD_SEED_5096:
                return "Marigold seed";
            case ItemList.NASTURTIUM_SEED_5098:
                return "Nasturtium seed";
            case ItemList.ROSEMARY_SEED_5097:
                return "Rosemary seed";
            case ItemList.WOAD_SEED_5099:
                return "Woad seed";
            case ItemList.LIMPWURT_SEED_5100:
                return "Limpwurt seed";

            // Bushes
            case ItemList.REDBERRY_SEED_5101:
                return "Redberry seed";
            case ItemList.CADAVABERRY_SEED_5102:
                return "Cadavaberry seed";
            case ItemList.DWELLBERRY_SEED_5103:
                return "Dwellberry seed";
            case ItemList.JANGERBERRY_SEED_5104:
                return "Jangerberry seed";
            case ItemList.WHITEBERRY_SEED_5105:
                return "Whiteberry seed";
            case ItemList.POISON_IVY_SEED_5106:
                return "Poison Ivy seed";

            // Special
            case ItemList.MUSHROOM_SPORE_5282:
                return "Mushroom spore";
            case ItemList.BELLADONNA_SEED_5281:
                return "Belladonna seed";
            case ItemList.CACTUS_SEED_5280:
                return "Cactus seed";
            case ItemList.SEAWEED_SPORE_21490:
                return "Seaweed spore";
            case ItemList.POTATO_CACTUS_3138:
                return "Potato cactus";

            // Herbs
            case ItemList.GUAM_SEED_5291:
                return "Guam seed";
            case ItemList.MARRENTILL_SEED_5292:
                return "Marrentill seed";
            case ItemList.TARROMIN_SEED_5293:
                return "Tarromin seed";
            case ItemList.HARRALANDER_SEED_5294:
                return "Harralander seed";
            case ItemList.RANARR_SEED_5295:
                return "Ranarr seed";
            case ItemList.TOADFLAX_SEED_5296:
                return "Toadflax seed";
            case ItemList.IRIT_SEED_5297:
                return "Irit seed";
            case ItemList.AVANTOE_SEED_5298:
                return "Avantoe seed";
            case ItemList.KWUARM_SEED_5299:
                return "Kwuarm seed";
            case ItemList.SNAPDRAGON_SEED_5300:
                return "Snapdragon seed";
            case ItemList.CADANTINE_SEED_5301:
                return "Cadantine seed";
            case ItemList.LANTADYME_SEED_5302:
                return "Lantadyme seed";
            case ItemList.DWARF_WEED_SEED_5303:
                return "Dwarf Weed seed";
            case ItemList.TORSTOL_SEED_5304:
                return "Torstol seed";

            default:
                throw new IllegalArgumentException("Unknown item ID: " + itemId);
        }
    }
}
