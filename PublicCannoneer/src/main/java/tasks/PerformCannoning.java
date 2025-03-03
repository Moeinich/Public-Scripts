package tasks;

import helpers.utils.ItemList;
import utils.Task;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static helpers.Interfaces.*;
import static main.PublicCannoneer.waitTime;
import static tasks.CheckForItems.checkedForItems;

public class PerformCannoning extends Task {
    private final int minWaitTime = 15; // Minimum wait time as defined in @ScriptConfiguration minMaxIntValues
    private final Random random = new Random();
    // Array of cannon colors
    List<Color> cannonColors =
            Arrays.asList(
                    Color.decode("#57575f"),
                    Color.decode("#6d6d76"),
                    Color.decode("#545459"),
                    Color.decode("#6e6e77"),
                    Color.decode("#57575f")
            );
    // Areas to look for
    Rectangle searchRect = new Rectangle(423, 206, 44, 89);
    Rectangle clickRect = new Rectangle(426, 221, 32, 35);
    private long lastExecutionTime = 0; // Tracks the last execution time
    private int delayMilliseconds = 0; // Holds the random delay in milliseconds

    public boolean activate() {
        return checkedForItems;
    }

    @Override
    public boolean execute() {
        boolean isCannonActive = Client.isAnyColorInRect(cannonColors, searchRect, 2);
        boolean hasCannonballs = Inventory.stackSize(ItemList.CANNONBALL_2) >= 1;

        if (Player.leveledUp()) {
            Condition.sleep(generateRandomDelay(200, 3000));
            Logger.log("We leveled up! clicking cannon again");
            clickCannon();
            Condition.sleep(generateRandomDelay(400, 800));
        }

        if (isCannonActive && hasCannonballs) {
            long currentTime = System.currentTimeMillis();

            // Generate a random delay between minWaitTime and y seconds if not already set
            if (delayMilliseconds == 0) {
                clickCannon();
                Random random = new Random();
                int delaySeconds = minWaitTime + random.nextInt(waitTime - minWaitTime + 1); // Generates a number between minWaitTime and maxWaitTime
                delayMilliseconds = delaySeconds * 1000;
                lastExecutionTime = currentTime; // Update last execution time
                Logger.log("New delay set: " + delaySeconds + " seconds starting from now");
                XpBar.getXP();
            }

            // Check if the current time is after the last execution time plus the delay
            if (currentTime - lastExecutionTime >= delayMilliseconds) {
                // Reset the delay so it can be recalculated next call
                delayMilliseconds = 0;
                return true;
            }
        } else if (!hasCannonballs) {
            Logger.log("No more cannon balls");
            Script.stop();
        } else {
            Logger.log("Could not find cannon.. Set it up!");
            Condition.sleep(5000); //Just sleep to avoid spam ;)
        }

        return false; // Return false if the delay has not yet passed
    }

    private void clickCannon() {
        Client.tap(clickRect);
        Logger.log("Clicking the cannon!.");
    }

    private int generateRandomDelay(int lowerBound, int upperBound) {
        // Swap if lowerBound is greater than upperBound
        if (lowerBound > upperBound) {
            int temp = lowerBound;
            lowerBound = upperBound;
            upperBound = temp;
        }
        return lowerBound + random.nextInt(upperBound - lowerBound + 1);
    }
}
