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
        version = "1.15",
        categories = {ScriptCategory.Minigames, ScriptCategory.Fishing},
        guideLink = "https://wiki.mufasaclient.com/docs/public-trawler/"
)
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name =  "Breaks",
                        description = "Would you like to break every few games?",
                        defaultValue = "true",
                        optionType = OptionType.BOOLEAN
                ),
                @ScriptConfiguration(
                        name =  "Break after",
                        description = "Please select the amount of games before you'd like to break? Keep in mind, this is randomised a bit.",
                        defaultValue = "3",
                        minMaxIntValues = {1, 8},
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

    @Override
    public void onStart(){
        Logger.log("Initialising PublicTrawler...");

        // Grab script config stuff
        Map<String, String> configs = getConfigurations();
        useBreaks = Boolean.parseBoolean((configs.get("Breaks")));
        gameAmount = Integer.parseInt((configs.get("Break after")));

        // Disable automatic breaks
        Client.disableBreakHandler();
        Chatbox.closeChatbox();
        checkPaste(); // Check swamp paste in inventory

        //Logs for debugging purposes
        Walker.setup(new MapChunk(new String[]{"40-50", "41-50", "40-49", "41-49", "30-75", "28-75", "31-75", "29-76", "29-75"}, "0", "1"));
        Logger.log("Starting PublicTrawler!");
    }

    @Override
    public void poll() {
        Logger.debugLog("GAME FLAG: " + GAME_FLAG);
        currentPos = Walker.getPlayerPosition();

        // Looped tasks go here.
        for (Task task : taskList) {
            if (task.activate()) {
                task.execute();
                return;
            }
        }
    }

    public static void checkPaste() {
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