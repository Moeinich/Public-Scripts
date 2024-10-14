package main;

import helpers.AbstractScript;
import helpers.ScriptCategory;
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
        version = "1.012",
        guideLink = "https://wiki.mufasaclient.com/docs/public-amethystminer/",
        categories = {ScriptCategory.Mining, ScriptCategory.Moneymaking}
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
                        name = "Use world hopper?",
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
            new Tile(12042, 38600, 0),
            new Tile(12065, 38632, 0)
    );
    public static Area mineArea = new Area(
            new Tile(12067, 38535, 0),
            new Tile(12123, 38586, 0)
    );
    public static Tile[] bankPath = new Tile[] {
            new Tile(12096, 38587, 0),
            new Tile(12086, 38607, 0),
            new Tile(12069, 38621, 0),
            new Tile(12053, 38620, 0)
    };
    public static Tile[] toMinePath = new Tile[] {
            new Tile(12070, 38621, 0),
            new Tile(12086, 38607, 0),
            new Tile(12094, 38592, 0),
            new Tile(12097, 38578, 0)
    };

    public static int[] clueIDs = {
            ItemList.CLUE_GEODE__BEGINNER__23442,
            ItemList.CLUE_GEODE__EASY__20358,
            ItemList.CLUE_GEODE__MEDIUM__20360,
            ItemList.CLUE_GEODE__HARD__20362,
            ItemList.CLUE_GEODE__ELITE__20364
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
    // Task list!
    List<Task> amethystTasks = Arrays.asList(
            new CheckPickaxe(),
            new DropGemsAndClues(),
            new Bank(),
            new Cut(),
            new MineAmethyst()
    );

    @Override
    public void onStart() {
        Logger.log("Setting everything up");

        Map<String, String> configs = getConfigurations();
        hopProfile = configs.get("Use world hopper?");
        hopEnabled = Boolean.parseBoolean(configs.get("Use world hopper?.enabled"));
        useWDH = Boolean.parseBoolean(configs.get("Use world hopper?.useWDH"));
        cutAmethysts = Boolean.parseBoolean(configs.get("Cut the amethyst"));
        craftOptionSelected = configs.get("Which craft option?");

        Walker.setup(new MapChunk(new String[]{"47-151", "46-151", "46-152", "47-152"}, "0"));
    }

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
}
