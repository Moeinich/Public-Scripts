package mTrawler;


import helpers.*;
import helpers.annotations.ScriptConfiguration;
import helpers.annotations.ScriptManifest;
import helpers.utils.*;
import mTrawler.Tasks.*;

import java.util.*;

import static helpers.Interfaces.*;

@ScriptManifest(
        name = "PublicTrawler",
        description = "Completes fishing trawler using swamp paste.",
        version = "2.01",
        categories = {ScriptCategory.Minigames, ScriptCategory.Fishing},
        guideLink = "https://wiki.mufasaclient.com/docs/public-trawler/"
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name =  "Stop after",
                        description = "After how many angler pieces would you like to stop the script? 4 by default (full outfit), 1-3 for if you need only a partial outfit, set to 0 to never stop.",
                        defaultValue = "4",
                        minMaxIntValues = {0, 4},
                        optionType = OptionType.INTEGER_SLIDER
                ),
                @ScriptConfiguration(
                        name =  "Breaks",
                        description = "Would you like to break every few games?",
                        defaultValue = "true",
                        optionType = OptionType.BOOLEAN
                ),
                @ScriptConfiguration(
                        name =  "Break after",
                        description = "Please select the amount of games before you'd like to break? Keep in mind, this is randomised a bit.",
                        defaultValue = "5",
                        minMaxIntValues = {1, 43},
                        optionType = OptionType.INTEGER
                )
        }
)

public class PublicTrawler extends AbstractScript {
    List<Task> taskList = Arrays.asList(
            new Port(),
            new Boat(),
            new Minigame(),
            new PortRewards(),
            new RunBack()
    );

    public static int GAME_FLAG = 0;
    public static boolean useBreaks;
    public static int gameAmount;
    public static Tile currentPos;
    public static int stopAfter;
    public static boolean stopAfterXPieces = false;

    // Paint indexes
    public static int anchoviesIndex;
    public static int tunaIndex;
    public static int lobsterIndex;
    public static int swordfishIndex;
    public static int sharkIndex;
    public static int seaturtleIndex;
    public static int mantarayIndex;
    public static int anglerpieceIndex;

    // Reward counts
    public static int anchoviesCount = 0;
    public static int tunaCount = 0;
    public static int lobsterCount = 0;
    public static int swordfishCount = 0;
    public static int sharkCount = 0;
    public static int seaturtleCount = 0;
    public static int mantarayCount = 0;
    public static int anglerpieceCount = 0;

    // Temp holder
    public static int tempCountHolder;

    @Override
    public void onStart(){
        Logger.log("Initialising PublicTrawler...");

        // Grab script config stuff
        Map<String, String> configs = getConfigurations();
        useBreaks = Boolean.parseBoolean((configs.get("Breaks")));
        gameAmount = Integer.parseInt((configs.get("Break after")));
        stopAfter = Integer.parseInt((configs.get("Stop after")));

        // Disable automatic breaks
        Client.disableBreakHandler();
        Chatbox.closeChatbox();
        checkPaste(); // Check swamp paste in inventory

        //Logs for debugging purposes
        Walker.setup(new MapChunk(new String[]{"40-50", "41-50", "40-49", "41-49", "30-75", "28-75", "31-75", "29-76", "29-75"}, "0", "1"));
        Logger.log("Starting PublicTrawler!");

        // Check if we want to stop after X angler pieces
        if (stopAfter == 0) {
            Logger.debugLog("stopAfter is set to 0, we won't stop after getting x pieces.");
        } else {
            Logger.debugLog("stopAfter is set to " + stopAfter + ", we still stop after getting " + stopAfter + " angler outfit pieces.");
            stopAfterXPieces = true;
        }

        // Set up paint bar
        Logger.debugLog("Creating paint...");
        Paint.Create(null);

        Paint.setStatus("Set up item paint boxes");

        // Set up the image boxes in paint
        anchoviesIndex = Paint.createBox("Anchovies", ItemList.RAW_ANCHOVIES_321, 0);
        Condition.sleep(generateRandomDelay(400, 600));
        tunaIndex = Paint.createBox("Tuna", ItemList.RAW_TUNA_359, 0);
        Condition.sleep(generateRandomDelay(400, 600));
        lobsterIndex = Paint.createBox("Lobster", ItemList.RAW_LOBSTER_377, 0);
        Condition.sleep(generateRandomDelay(400, 600));
        swordfishIndex = Paint.createBox("Swordfish", ItemList.RAW_SWORDFISH_371, 0);
        Condition.sleep(generateRandomDelay(400, 600));
        sharkIndex = Paint.createBox("Shark", ItemList.RAW_SHARK_383, 0);
        Condition.sleep(generateRandomDelay(400, 600));
        seaturtleIndex = Paint.createBox("Sea Turtle", ItemList.RAW_SEA_TURTLE_395, 0);
        Condition.sleep(generateRandomDelay(400, 600));
        mantarayIndex = Paint.createBox("Manta Ray", ItemList.RAW_MANTA_RAY_389, 0);
        Condition.sleep(generateRandomDelay(400, 600));
        anglerpieceIndex = Paint.createBox("Angler piece", ItemList.ANGLER_TOP_13259, 0);
        Condition.sleep(generateRandomDelay(400, 600));

        Paint.setStatus("Start script");
    }

    @Override
    public void poll() {
        Logger.debugLog("GAME FLAG: " + GAME_FLAG);
        currentPos = Walker.getPlayerPosition();

        if (!GameTabs.isTabOpen(UITabs.INVENTORY)) {
            GameTabs.openTab(UITabs.INVENTORY);
        }

        // Looped tasks go here.
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }

    public static void checkPaste() {
        Paint.setStatus("Check swamp paste");
        Logger.debugLog("Checking inventory has swamp paste.");
        if(!GameTabs.isTabOpen(UITabs.INVENTORY)) {
            GameTabs.openTab(UITabs.INVENTORY);
        }
        if (Inventory.stackSize(1941) < 60){
            Logger.log("No swamp paste, stopping script.");
            Logout.logout();
            Script.stop();
        }
        Logger.debugLog("Amount of paste: " + Inventory.stackSize(1941));
        Logger.debugLog("Inventory has enough swamp paste, continuing..");
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