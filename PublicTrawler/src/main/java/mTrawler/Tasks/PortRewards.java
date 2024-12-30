package mTrawler.Tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import mTrawler.Task;

import static mTrawler.PublicTrawler.*;
import static mTrawler.Tasks.Minigame.inPosition;

import java.awt.*;

import static helpers.Interfaces.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class PortRewards extends Task {
    Tile rewardTile = new Tile(10667, 12413, 0);
    Rectangle rewardsTap = new Rectangle(417, 198, 38, 49);
    Rectangle depositBank = new Rectangle(489, 442, 26, 27);
    private final Random random = new Random();
    Area portArea = new Area(
            new Tile(10599, 12334, 0),
            new Tile(10740, 12487, 0)
    );
    private static Integer breakCounter = 0;
    private static final Integer initialGameAmount = gameAmount;

    @Override
    public boolean activate() {
        // if player at port but not on boat
        return (Player.isTileWithinArea(currentPos, portArea) && GAME_FLAG == 1);
    }

    @Override //the code to execute if criteria met
    public boolean execute() {
        resetGameParametersIfNeeded();

        moveToRewardTileAndCollectRewards();
        XpBar.getXP();
        checkPaste();
        GAME_FLAG = 0;

        handleBreaksIfNeeded();

        return true;
    }

    private void resetGameParametersIfNeeded() {
        if (inPosition) {
            inPosition = false;
            Logger.debugLog("Reset game parameters successful");
        }
    }

    private void moveToRewardTileAndCollectRewards() {
        Walker.step(rewardTile);
        Condition.wait(() -> Player.tileEquals(currentPos, rewardTile), 200, 20);

        tapAndSleep(rewardsTap);
        tapAndSleep(depositBank);
    }

    private void tapAndSleep(Rectangle tapArea) {
        Client.tap(tapArea);
        Condition.sleep(1300 + random.nextInt(500));
    }

    private void handleBreaksIfNeeded() {
        if (useBreaks) {
            breakCounter++;
            if (breakCounter >= gameAmount) {
                Logger.log("We are now taking a break after " + breakCounter + " games.");
                breakCounter = 0;
                randomizeGameAmount();

                Logout.logout();
                sleepRandomDuration(); // Sleep between 2 and 6 minutes
                Login.login();
            } else {
                Logger.log("Continuing without a break. Current break counter: " + breakCounter);
            }
        }
    }

    private void sleepRandomDuration() {
        int randomDuration = ThreadLocalRandom.current().nextInt(120000, 360000 + 1);
        Condition.sleep(randomDuration);
    }

    private void randomizeGameAmount() {
        int lowerBound = initialGameAmount > 1 ? initialGameAmount - (initialGameAmount <= 5 ? 1 : 2) : 1;
        int upperBound = initialGameAmount + (initialGameAmount <= 5 ? 1 : 2);

        // Ensure the bounds are valid
        if (upperBound <= lowerBound) {
            Logger.debugLog("Invalid bounds for randomization: lowerBound=" + lowerBound + ", upperBound=" + upperBound);
            gameAmount = lowerBound; // Default to lowerBound
        } else {
            gameAmount = random.nextInt(upperBound - lowerBound) + lowerBound;
        }

        Logger.debugLog("Game amount randomized for the next cycle: " + gameAmount);
    }
}
