package tasks;

import utils.Task;

import java.util.Random;

import static helpers.Interfaces.*;
import static main.PublicCannoneer.breakEvery;
import static main.PublicCannoneer.generateRandomDelay;

public class BreakManager extends Task {
    private static final Random random = new Random(System.nanoTime());
    public static int shouldBreakAt = 0;
    public static boolean shouldBreakNow = false;

    private static void generateRandomNextBreakCount() {
        int variability = random.nextInt(11) - 5;  // Range [-5, 5]
        shouldBreakAt = breakEvery + variability;
        Logger.log("Next break scheduled after " + shouldBreakAt + " minutes.");
    }

    @Override
    public boolean activate() {
        if (shouldBreakAt == 0) {
            generateRandomNextBreakCount();
        }

        if (Player.leveledUp()) {
            Client.sendKeystroke("KEYCODE_SPACE");
            Condition.sleep(generateRandomDelay(1000, 2000));
        }


        return shouldBreakNow;
    }

    @Override
    public boolean execute() {
        int breakMinutes = random.nextInt(4) + 1;  // Generates a number from 1 to 4
        int breakSeconds = random.nextInt(60);     // Use the same random instance here as well
        int breakMillis = breakMinutes * 60000 + breakSeconds * 1000;  // Convert minutes and seconds to milliseconds

        // Log the precise break duration
        Logger.log("Taking a break for " + breakMinutes + " minute(s) and " + breakSeconds + " second(s).");
        Paint.setStatus("Taking a break for 0" + breakMinutes + ":" + breakSeconds);

        // Breaking logic
        Logout.logout();
        Condition.sleep(breakMillis);  // Sleep for the calculated duration

        Logger.log("Break over, resuming script.");
        Paint.setStatus("Break over, resuming script");
        Paint.setStatus("Logging in");
        Login.login();

        generateRandomNextBreakCount();
        shouldBreakNow = false;
        return true;
    }
}
