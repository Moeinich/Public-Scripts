package mTrawler.Tasks;

import helpers.utils.Area;
import helpers.utils.Tile;
import mTrawler.Task;

import static mTrawler.PublicTrawler.*;
import static mTrawler.Tasks.Minigame.inPosition;

import java.awt.*;

import static helpers.Interfaces.*;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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


    // Fish colors
    private static final Color mantaColor = Color.decode("#525c36");
    private static final Color seaturtleColor = Color.decode("#736423");
    private static final Color sharkColor = Color.decode("#6a6160");
    private static final Color swordfishColor = Color.decode("#b47bbf");
    private static final Color lobsterColor = Color.decode("#6e3914");
    private static final Color tunaColor = Color.decode("#9b8f8e");
    private static final Color anchoviesColor = Color.decode("#5e5f7b");
    private static final List<Color> anglerOutfitColors = Arrays.asList(
            // Boots
            Color.decode("#292f00"),
            Color.decode("#121400"),
            // Top and bottom
            Color.decode("#5a5a39"),
            Color.decode("#37371c")

            // Hat also falls under these four.
    );

    // Reward boxes
    private static final Rectangle rewardBox1 = new Rectangle(133, 225, 55, 51);
    private static final Rectangle rewardBox2 = new Rectangle(190, 225, 55, 51);
    private static final Rectangle rewardBox3 = new Rectangle(247, 225, 55, 51);
    private static final Rectangle rewardBox4 = new Rectangle(304, 225, 55, 51);
    private static final Rectangle rewardBox5 = new Rectangle(361, 225, 55, 51);
    private static final Rectangle rewardBox6 = new Rectangle(418, 225, 55, 51);
    private static final Rectangle rewardBox7 = new Rectangle(475, 225, 55, 51);

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
            Paint.setStatus("Reset game parameters");
            inPosition = false;
            Logger.debugLog("Reset game parameters successful");
        }
    }

    private void moveToRewardTileAndCollectRewards() {
        Paint.setStatus("Collect rewards");
        Walker.step(rewardTile);
        Condition.wait(() -> Player.tileEquals(currentPos, rewardTile), 200, 20);

        // Open rewards interface
        tapAndSleep(rewardsTap);

        // Update the paintBar / rewards
        updatePaintBar();

        // Deposit all rewards to the bank
        tapAndSleep(depositBank);

        if (stopAfterXPieces) {
            Logger.debugLog("Current angler pieces obtained: " + anglerpieceCount + "/" + stopAfter);

            if (anglerpieceCount == stopAfter || anglerpieceCount > stopAfter) {
                Logger.log("Reached the angler piece outfit goal, logging out and stopping script!");

                Logout.logout();
                Script.stop();
            }
        }
    }

    private void tapAndSleep(Rectangle tapArea) {
        Client.tap(tapArea);
        Condition.sleep(generateRandomDelay(1300, 1800));
    }

    private void updatePaintBar() {
        Paint.setStatus("Update paint bar");
        // List of reward boxes
        List<Rectangle> rewardBoxes = Arrays.asList(
                rewardBox1, rewardBox2, rewardBox3, rewardBox4,
                rewardBox5, rewardBox6, rewardBox7
        );

        // List of colors to check
        List<Color> fishColors = Arrays.asList(
                mantaColor, seaturtleColor, sharkColor,
                swordfishColor, lobsterColor, tunaColor,
                anchoviesColor
        );

        // Iterate through each reward box
        for (int i = 0; i < rewardBoxes.size(); i++) {
            Rectangle box = rewardBoxes.get(i);
            boolean colorFound = false;

            // Check each color in the current box
            for (Color color : fishColors) {
                if (Client.isColorInRect(color, box, 5)) { // Threshold of 5
                    colorFound = true;

                    // Execute code for the found color
                    Logger.debugLog("Color " + color + " found in rewardBox" + (i + 1));
                    executeActionForColor(color, i + 1); // Custom method for specific color actions
                    Condition.sleep(generateRandomDelay(150, 300));

                    break; // Exit color loop if a color is found
                }
            }

            // Check for angler outfit colors
            if (!colorFound) {
                for (Color anglerColor : anglerOutfitColors) {
                    if (Client.isColorInRect(anglerColor, box, 5)) {
                        colorFound = true;
                        Logger.debugLog("Angler outfit piece detected in rewardBox" + (i + 1));
                        executeActionForAnglerPiece(i + 1);
                        Condition.sleep(generateRandomDelay(150, 300));
                        break;
                    }
                }
            }

            // If no color is found in the current box
            if (!colorFound) {
                Logger.debugLog("No matching color found in rewardBox" + (i + 1));
            }
        }
    }

    private void handleBreaksIfNeeded() {
        if (useBreaks) {
            breakCounter++;
            if (breakCounter >= gameAmount) {
                Logger.log("We are now taking a break after " + breakCounter + " games.");
                breakCounter = 0;
                randomizeGameAmount();

                Logout.logout();
                Condition.sleep(generateRandomDelay(120000, 360000)); // Sleep between 2 and 6 minutes
                Login.login();
            } else {
                Logger.log("Continuing without a break. Current break counter: " + breakCounter);
            }
        }
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

    private void executeActionForColor(Color color, int boxIndex) {
        Logger.debugLog(getFishName(color) + " detected in box " + boxIndex);
        tempCountHolder = interfaces.readStackSize(getRewardBox(boxIndex));
        int currentCount = (tempCountHolder == -1 || tempCountHolder == 0) ? 1 : tempCountHolder;

        updateFishCount(color, currentCount);
    }

    private void executeActionForAnglerPiece(int boxIndex) {
        Logger.debugLog("Angler outfit piece detected in box " + boxIndex);
        anglerpieceCount++;
        Paint.updateBox(anglerpieceIndex, anglerpieceCount);

    }

    private Rectangle getRewardBox(int boxIndex) {
        if (boxIndex == 1) {
            return rewardBox1;
        } else if (boxIndex == 2) {
            return rewardBox2;
        } else if (boxIndex == 3) {
            return rewardBox3;
        } else if (boxIndex == 4) {
            return rewardBox4;
        } else if (boxIndex == 5) {
            return rewardBox5;
        } else if (boxIndex == 6) {
            return rewardBox6;
        } else if (boxIndex == 7) {
            return rewardBox7;
        } else return new Rectangle(1, 1, 1, 1);
    }

    private String getFishName(Color color) {
        if (color.equals(mantaColor)) return "Manta Ray";
        if (color.equals(seaturtleColor)) return "Sea Turtle";
        if (color.equals(sharkColor)) return "Shark";
        if (color.equals(swordfishColor)) return "Swordfish";
        if (color.equals(lobsterColor)) return "Lobster";
        if (color.equals(tunaColor)) return "Tuna";
        if (color.equals(anchoviesColor)) return "Anchovies";
        return "Unknown Fish";
    }

    private void updateFishCount(Color color, int count) {
        if (color.equals(mantaColor)) {
            mantarayCount += count;
            Paint.updateBox(mantarayIndex, mantarayCount);
        } else if (color.equals(seaturtleColor)) {
            seaturtleCount += count;
            Paint.updateBox(seaturtleIndex, seaturtleCount);
        } else if (color.equals(sharkColor)) {
            sharkCount += count;
            Paint.updateBox(sharkIndex, sharkCount);
        } else if (color.equals(swordfishColor)) {
            swordfishCount += count;
            Paint.updateBox(swordfishIndex, swordfishCount);
        } else if (color.equals(lobsterColor)) {
            lobsterCount += count;
            Paint.updateBox(lobsterIndex, lobsterCount);
        } else if (color.equals(tunaColor)) {
            tunaCount += count;
            Paint.updateBox(tunaIndex, tunaCount);
        } else if (color.equals(anchoviesColor)) {
            anchoviesCount += count;
            Paint.updateBox(anchoviesIndex, anchoviesCount);
        }
    }
}
