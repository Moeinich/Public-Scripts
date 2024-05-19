package main;

import helpers.*;
import helpers.annotations.AllowedValue;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.*;
import tasks.*;
import utils.Task;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static helpers.Interfaces.*;

@ScriptManifest(
        name = "Public Amethyst Miner",
        description = "An easy to use Amethyst Miner. Feel free to contribute: https://github.com/Moeinich/Public-Scripts",
        version = "1.0",
        guideLink = "https://wiki.mufasaclient.com/docs/public-amethystminer/",
        categories = {ScriptCategory.Mining}
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "Cut the amethyst",
                        description = "Cut the amethyst instead of banking them",
                        defaultValue = "false",
                        optionType = OptionType.BOOLEAN
                ),
                @ScriptConfiguration(
                  name = "Which craft option?",
                        description = "If cutting, which craft option would you like to do?",
                        defaultValue = "Amethyst bolt tips",
                        allowedValues = {
                                @AllowedValue(optionIcon = "3951", optionName = "Amethyst bolt tips"),
                                @AllowedValue(optionIcon = "21350", optionName = "Amethyst arrowtips"),
                                @AllowedValue(optionIcon = "3963", optionName = "Amethyst javelin heads"),
                                @AllowedValue(optionIcon = "25853", optionName = "Amethyst dart tip"),

                        },
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name =  "Use world hopper?",
                        description = "Would you like to hop worlds based on your hop profile settings?",
                        defaultValue = "true",
                        optionType = OptionType.WORLDHOPPER
                )
        }
)

public class PublicAmethystMiner extends AbstractScript {
    public static String hopProfile;
    public static Boolean hopEnabled;
    public static Boolean useWDH;
    public static boolean cutAmethysts;
    public static int pickaxeInventorySlotNumber = 0;
    public static Boolean pickaxeEquipped = false;
    public static RegionBox miningGuild = new RegionBox("MINING_GUILD", 6291, 459, 6654, 777);
    public static Area bankArea = new Area(
            new Tile(2124, 205),
            new Tile(2134, 216)
    );
    public static Area mineArea = new Area(
            new Tile(2133, 215),
            new Tile(2156, 236)
    );
    public static Tile[] bankPath = new Tile[] {
            new Tile(2143, 221),
            new Tile(2139, 212),
            new Tile(2129,210)
    };

    public static int[] clueIDs = {
            ItemList.CLUE_GEODE_BEGINNER_23442,
            ItemList.CLUE_GEODE_EASY_20358,
            ItemList.CLUE_GEODE_MEDIUM_20360,
            ItemList.CLUE_GEODE_HARD_20362,
            ItemList.CLUE_GEODE_ELITE_20364
    };
    public static int[] gemIDs = {
            ItemList.UNCUT_OPAL_1625,
            ItemList.UNCUT_JADE_1627,
            ItemList.UNCUT_RED_TOPAZ_1629,
            ItemList.UNCUT_SAPPHIRE_1623,
            ItemList.UNCUT_SAPPHIRE_1623,
            ItemList.UNCUT_EMERALD_1621,
            ItemList.UNCUT_RUBY_1619,
            ItemList.UNCUT_DIAMOND_1617
    };

    public static String craftOptionSelected;

    @Override
    public void onStart(){
        Logger.log("Setting everything up");

        Map<String, String> configs = getConfigurations();
        hopProfile = configs.get("Use world hopper?");
        hopEnabled = Boolean.parseBoolean(configs.get("Use world hopper?.enabled"));
        useWDH = Boolean.parseBoolean(configs.get("Use world hopper?.useWDH"));
        cutAmethysts = Boolean.parseBoolean(configs.get("Cut the amethyst"));
        craftOptionSelected = configs.get("Which craft option?");
    }

    // Task list!
    List<Task> amethystTasks = Arrays.asList(
            new CheckPickaxe(),
            new DropGemsAndClues(),
            new Bank(),
            new Cut(),
            new MineAmethyst()
    );

    @Override
    public void poll() {
        if (!GameTabs.isInventoryTabOpen()) {
            GameTabs.openInventoryTab();
            Condition.wait(() -> GameTabs.isInventoryTabOpen(), 100, 20);
        }

        //Run tasks
        for (Task task : amethystTasks) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }

    public static Tile[] reverseTiles(Tile[] original) {
        Tile[] reversed = new Tile[original.length];
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        return reversed;
    }
}
